CREATE TABLE IF NOT EXISTS vital_sign_count_metrics (
  id VARCHAR(255) NOT NULL,
  vital_sign_type VARCHAR(255),
  total INTEGER,
  mean DOUBLE PRECISION,
  count_news_0 INTEGER,
  count_news_1 INTEGER,
  count_news_2 INTEGER,
  count_news_3 INTEGER,
  registered_at TIMESTAMP,
  PRIMARY KEY (id)
);