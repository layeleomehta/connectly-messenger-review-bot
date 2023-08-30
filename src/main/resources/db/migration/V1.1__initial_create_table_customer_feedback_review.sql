CREATE TABLE customer_feedback_review
(
	id                                  SERIAL PRIMARY KEY,
	external_id                         VARCHAR(128) UNIQUE NOT NULL,
	business_page_id                    INTEGER NOT NULL REFERENCES business_page(id),
	review_text                         TEXT,
	star_rating                         SMALLINT NOT NULL CHECK (star_rating >= 1 AND star_rating <= 5)
);
