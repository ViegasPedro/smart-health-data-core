CREATE TABLE IF NOT EXISTS vital_sign_user_metrics (
  id VARCHAR(255) PRIMARY KEY,
  registered_at TIMESTAMP,
  covid_total INTEGER,
  covid_elderly INTEGER,
  covid_male INTEGER,
  covid_female INTEGER,
  pneumonia_total INTEGER,
  pneumonia_elderly INTEGER,
  pneumonia_male INTEGER,
  pneumonia_female INTEGER
);