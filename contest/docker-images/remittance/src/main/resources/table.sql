CREATE TABLE MEMBER (
    INDEX BIGINT AUTO_INCREMENT,
    USER_ID VARCHAR(255) UNIQUE NOT NULL,
    PASSWORD VARCHAR(255) NOT NULL,
    NAME VARCHAR(255) NOT NULL,
    ID_TYPE VARCHAR(255) NOT NULL,
    ID_VALUE VARCHAR(255) NOT NULL
);

CREATE TABLE remittance_quote (
    quote_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    source_amount BIGINT NOT NULL,
    fee double NOT NULL,
    usd_exchange_rate double NOT NULL,
    usd_amount double NOT NULL,
    target_currency VARCHAR(255) NOT NULL,
    exchange_rate double NOT NULL,
    target_amount double NOT NULL,
    expire_time TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE TABLE remittance_log (
    log_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    source_amount BIGINT NOT NULL,
    fee double NOT NULL,
    usd_exchange_rate double NOT NULL,
    usd_amount double NOT NULL,
    target_currency VARCHAR(255) NOT NULL,
    exchange_rate double NOT NULL,
    target_amount double NOT NULL,
    requested_date TIMESTAMP WITH TIME ZONE NOT NULL,
    user_id VARCHAR(255) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES member(user_id)
);

CREATE TABLE MEMBER_v2 (
    INDEX UUID PRIMARY KEY,
    USER_ID VARCHAR(255) UNIQUE NOT NULL,
    PASSWORD VARCHAR(255) NOT NULL,
    NAME VARCHAR(255) NOT NULL,
    ID_TYPE VARCHAR(255) NOT NULL,
    ID_VALUE VARCHAR(255) NOT NULL
);

CREATE TABLE remittance_quote_v2 (
    quote_id UUID PRIMARY KEY,
    source_amount BIGINT NOT NULL,
    fee decimal(10, 0) NOT NULL,
    usd_exchange_rate decimal(10, 0) NOT NULL,
    usd_amount decimal(10, 0) NOT NULL,
    target_currency VARCHAR(255) NOT NULL,
    exchange_rate decimal(10, 0) NOT NULL,
    target_amount decimal(10, 0) NOT NULL,
    expire_time TIMESTAMP WITH TIME ZONE NOT NULL,
    user_id VARCHAR(255) NOT NULL
);

CREATE TABLE remittance_log_v2 (
    log_id UUID PRIMARY KEY,
    source_amount BIGINT NOT NULL,
    fee decimal(10, 0) NOT NULL,
    usd_exchange_rate decimal(10, 0) NOT NULL,
    usd_amount decimal(10, 0) NOT NULL,
    target_currency VARCHAR(255) NOT NULL,
    exchange_rate decimal(10, 0) NOT NULL,
    target_amount decimal(10, 0) NOT NULL,
    requested_date TIMESTAMP WITH TIME ZONE NOT NULL,
    user_id VARCHAR(255) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES member_v2(user_id)
);


select * from member_v2;

select * from remittance_quote_v2;

select * from remittance_log_v2;

DROP ALL OBJECTS;

