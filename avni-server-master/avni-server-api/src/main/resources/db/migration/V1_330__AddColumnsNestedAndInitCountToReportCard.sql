ALTER TABLE public.report_card
    ADD COLUMN IF NOT EXISTS nested BOOLEAN DEFAULT FALSE,
    ADD COLUMN IF NOT EXISTS count_of_cards SMALLINT NOT NULL DEFAULT 1;
