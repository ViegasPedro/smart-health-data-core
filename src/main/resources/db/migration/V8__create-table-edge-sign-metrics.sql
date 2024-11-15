CREATE TABLE IF NOT EXISTS vital_sign_edge_metrics (
  id VARCHAR(255) NOT NULL,
  vital_sign_type VARCHAR(255),
  edge_id VARCHAR(255),
  average_news_score DOUBLE PRECISION,
  registered_at TIMESTAMP,
  PRIMARY KEY (id)
);

ALTER TABLE vital_sign_edge_metrics
  ADD CONSTRAINT FK_vital_sign_edge_metrics
  FOREIGN KEY (edge_id)
  REFERENCES edge (id);
