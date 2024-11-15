CREATE TABLE IF NOT EXISTS user_vital_sign_aggregate (
  id VARCHAR(255) PRIMARY KEY,
  patient_id VARCHAR(255),
  last_update TIMESTAMP,
  last_temperature_value DOUBLE PRECISION,
  last_heart_rate_value DOUBLE PRECISION,
  last_blood_pressure_value DOUBLE PRECISION,
  last_respiration_rate_value DOUBLE PRECISION,
  last_spo2_value DOUBLE PRECISION,
  last_oxygen_value VARCHAR(255),
  last_consciousness_value VARCHAR(255)
);

ALTER TABLE user_vital_sign_aggregate
  ADD CONSTRAINT FK_user_vital_sign_aggregate
  FOREIGN KEY (patient_id)
  REFERENCES patient (id);
