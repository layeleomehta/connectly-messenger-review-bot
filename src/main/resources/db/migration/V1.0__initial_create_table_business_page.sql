CREATE TABLE business_page(
	id                                  SERIAL PRIMARY KEY,
	external_id                         VARCHAR(128) UNIQUE NOT NULL,
	business_name                       VARCHAR(128) NOT NULL,
	hashed_page_access_token            TEXT NOT NULL,
	page_id                             VARCHAR(128) UNIQUE NOT NULL
);
