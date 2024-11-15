CREATE TABLE IF NOT EXISTS alert_vital_sign (
  id VARCHAR(255) NOT NULL,
  type VARCHAR(255),
  "date" TIMESTAMP,
  "value" VARCHAR(255),
  news_score INTEGER,
  edge_id VARCHAR(255),
  sensor_id VARCHAR(255),
  user_id VARCHAR(255),
  PRIMARY KEY (id)
);