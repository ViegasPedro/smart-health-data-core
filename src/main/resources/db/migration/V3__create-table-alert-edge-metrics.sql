CREATE TABLE IF NOT EXISTS edge (
  id VARCHAR(255) PRIMARY KEY,
  latitude DECIMAL(9,6),
  longitude DECIMAL(9,6)
);

CREATE TABLE IF NOT EXISTS alert_edge_metrics (
  id VARCHAR(255) PRIMARY KEY,
  edge_id VARCHAR(255) NOT NULL,
  total_alerts INTEGER,
  registered_at TIMESTAMP
);

ALTER TABLE alert_edge_metrics
  ADD CONSTRAINT FK_alert_edge_metrics
  FOREIGN KEY (edge_id)
  REFERENCES edge (id);

ALTER TABLE alert
  ADD CONSTRAINT FK_alert
  FOREIGN KEY (edge_id)
  REFERENCES edge (id);
