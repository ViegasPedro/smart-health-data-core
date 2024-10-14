CREATE TABLE IF NOT EXISTS alert_metrics (
  id VARCHAR(255) PRIMARY KEY,
  total INTEGER,
  single_vital_sign INTEGER,
  multi_vital_sign INTEGER,
  registered_at TIMESTAMP,
  users INTEGER
);