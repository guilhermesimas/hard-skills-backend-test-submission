INSERT INTO vessel (code) VALUES ('MV123');
INSERT INTO vessel (code) VALUES ('MV456');
INSERT INTO equipment (name, location, code, vessel_code, status) VALUES ('alwayshere', 'Brazil', 'MV409', 'MV123', 'active');
INSERT INTO equipment (name, location, code, vessel_code, status) VALUES ('shouldappear', 'Brazil', 'MVOK', 'MV456', 'active');
INSERT INTO equipment (name, location, code, vessel_code, status) VALUES ('shouldnotappear', 'Brazil', 'MVNOK', 'MV456', 'inactive');
