--
-- PostgreSQL database dump
--

-- Dumped from database version 9.6.0
-- Dumped by pg_dump version 12.3

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

--
-- Name: candidate; Type: TABLE; Schema: public; Owner: testuser
--

CREATE TABLE public.candidate (
    id bigint NOT NULL,
    question_id bigint NOT NULL,
    description character varying(256) NOT NULL
);


ALTER TABLE public.candidate OWNER TO testuser;

--
-- Name: candidate_id_seq; Type: SEQUENCE; Schema: public; Owner: testuser
--

CREATE SEQUENCE public.candidate_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.candidate_id_seq OWNER TO testuser;

--
-- Name: candidate_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: testuser
--

ALTER SEQUENCE public.candidate_id_seq OWNED BY public.candidate.id;


--
-- Name: questions; Type: TABLE; Schema: public; Owner: testuser
--

CREATE TABLE public.questions (
    id bigint NOT NULL,
    title character varying(256) NOT NULL,
    sentence character varying(256) NOT NULL,
    until timestamp without time zone NOT NULL,
    created_by bigint NOT NULL
);


ALTER TABLE public.questions OWNER TO testuser;

--
-- Name: questions_id_seq; Type: SEQUENCE; Schema: public; Owner: testuser
--

CREATE SEQUENCE public.questions_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.questions_id_seq OWNER TO testuser;

--
-- Name: questions_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: testuser
--

ALTER SEQUENCE public.questions_id_seq OWNED BY public.questions.id;


--
-- Name: users; Type: TABLE; Schema: public; Owner: testuser
--

CREATE TABLE public.users (
    id bigint NOT NULL,
    user_name character varying(256) NOT NULL,
    hash character varying(60) NOT NULL,
    bio character varying(256)
);


ALTER TABLE public.users OWNER TO testuser;

--
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: testuser
--

CREATE SEQUENCE public.users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.users_id_seq OWNER TO testuser;

--
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: testuser
--

ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;


--
-- Name: voting; Type: TABLE; Schema: public; Owner: testuser
--

CREATE TABLE public.voting (
    id bigint NOT NULL,
    question_id bigint NOT NULL,
    voted_by bigint NOT NULL,
    candidate_id bigint NOT NULL
);


ALTER TABLE public.voting OWNER TO testuser;

--
-- Name: voting_id_seq; Type: SEQUENCE; Schema: public; Owner: testuser
--

CREATE SEQUENCE public.voting_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.voting_id_seq OWNER TO testuser;

--
-- Name: voting_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: testuser
--

ALTER SEQUENCE public.voting_id_seq OWNED BY public.voting.id;


--
-- Name: candidate id; Type: DEFAULT; Schema: public; Owner: testuser
--

ALTER TABLE ONLY public.candidate ALTER COLUMN id SET DEFAULT nextval('public.candidate_id_seq'::regclass);


--
-- Name: questions id; Type: DEFAULT; Schema: public; Owner: testuser
--

ALTER TABLE ONLY public.questions ALTER COLUMN id SET DEFAULT nextval('public.questions_id_seq'::regclass);


--
-- Name: users id; Type: DEFAULT; Schema: public; Owner: testuser
--

ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);


--
-- Name: voting id; Type: DEFAULT; Schema: public; Owner: testuser
--

ALTER TABLE ONLY public.voting ALTER COLUMN id SET DEFAULT nextval('public.voting_id_seq'::regclass);


--
-- Data for Name: candidate; Type: TABLE DATA; Schema: public; Owner: testuser
--

COPY public.candidate (id, question_id, description) FROM stdin;
\.


--
-- Data for Name: questions; Type: TABLE DATA; Schema: public; Owner: testuser
--

COPY public.questions (id, title, sentence, until, created_by) FROM stdin;
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: testuser
--

COPY public.users (id, user_name, hash, bio) FROM stdin;
\.


--
-- Data for Name: voting; Type: TABLE DATA; Schema: public; Owner: testuser
--

COPY public.voting (id, question_id, voted_by, candidate_id) FROM stdin;
\.


--
-- Name: candidate_id_seq; Type: SEQUENCE SET; Schema: public; Owner: testuser
--

SELECT pg_catalog.setval('public.candidate_id_seq', 1, false);


--
-- Name: questions_id_seq; Type: SEQUENCE SET; Schema: public; Owner: testuser
--

SELECT pg_catalog.setval('public.questions_id_seq', 1, false);


--
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: testuser
--

SELECT pg_catalog.setval('public.users_id_seq', 1, false);


--
-- Name: voting_id_seq; Type: SEQUENCE SET; Schema: public; Owner: testuser
--

SELECT pg_catalog.setval('public.voting_id_seq', 1, false);


--
-- Name: candidate candidate_pkey; Type: CONSTRAINT; Schema: public; Owner: testuser
--

ALTER TABLE ONLY public.candidate
    ADD CONSTRAINT candidate_pkey PRIMARY KEY (id);


--
-- Name: questions questions_pkey; Type: CONSTRAINT; Schema: public; Owner: testuser
--

ALTER TABLE ONLY public.questions
    ADD CONSTRAINT questions_pkey PRIMARY KEY (id);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: testuser
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: users users_user_name_unique; Type: CONSTRAINT; Schema: public; Owner: testuser
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_user_name_unique UNIQUE (user_name);


--
-- Name: voting voting_pkey; Type: CONSTRAINT; Schema: public; Owner: testuser
--

ALTER TABLE ONLY public.voting
    ADD CONSTRAINT voting_pkey PRIMARY KEY (id);


--
-- Name: voting voting_question_id_voted_by_unique; Type: CONSTRAINT; Schema: public; Owner: testuser
--

ALTER TABLE ONLY public.voting
    ADD CONSTRAINT voting_question_id_voted_by_unique UNIQUE (question_id, voted_by);


--
-- Name: candidate fk_candidate_question_id_id; Type: FK CONSTRAINT; Schema: public; Owner: testuser
--

ALTER TABLE ONLY public.candidate
    ADD CONSTRAINT fk_candidate_question_id_id FOREIGN KEY (question_id) REFERENCES public.questions(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- Name: questions fk_questions_created_by_id; Type: FK CONSTRAINT; Schema: public; Owner: testuser
--

ALTER TABLE ONLY public.questions
    ADD CONSTRAINT fk_questions_created_by_id FOREIGN KEY (created_by) REFERENCES public.users(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- Name: voting fk_voting_candidate_id_id; Type: FK CONSTRAINT; Schema: public; Owner: testuser
--

ALTER TABLE ONLY public.voting
    ADD CONSTRAINT fk_voting_candidate_id_id FOREIGN KEY (candidate_id) REFERENCES public.candidate(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- Name: voting fk_voting_question_id_id; Type: FK CONSTRAINT; Schema: public; Owner: testuser
--

ALTER TABLE ONLY public.voting
    ADD CONSTRAINT fk_voting_question_id_id FOREIGN KEY (question_id) REFERENCES public.questions(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- Name: voting fk_voting_voted_by_id; Type: FK CONSTRAINT; Schema: public; Owner: testuser
--

ALTER TABLE ONLY public.voting
    ADD CONSTRAINT fk_voting_voted_by_id FOREIGN KEY (voted_by) REFERENCES public.users(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- PostgreSQL database dump complete
--

