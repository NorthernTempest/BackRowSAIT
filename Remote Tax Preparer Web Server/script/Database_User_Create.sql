CREATE USER 'remote_tax_preparer_app'
IDENTIFIED BY 'perrywinkle69';

GRANT SELECT, INSERT, UPDATE, DELETE ON tax_preparer_db.* TO remote_tax_preparer_app;