CREATE TABLE IF NOT EXISTS alert (
  id VARCHAR(255) NOT NULL,
  "date" TIMESTAMP,
  score_type VARCHAR(255),
  news_score INTEGER,
  edge_id VARCHAR(255),
  sensor_id VARCHAR(255),
  user_id VARCHAR(255),
  PRIMARY KEY (id)
);
