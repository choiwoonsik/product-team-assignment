create table `pos_easy_connection_agreement_record`
(
    id                              bigint          not null auto_increment,
    registration_number             varchar(255)    not null comment '사업자 등록 번호',
    agreement_type                  varchar(255)    not null comment 'POS 카드 실시간 데이터 연동 동의 유형(POS_VERIFIY_CONNECTABLE, POS_CONNECT)',
    is_agreed_yn                    varchar(255)    not null comment '동의 여부(Y/N)',
    date_created                    datetime(6)     not null comment '생성일시',
    date_updated                    datetime(6)     not null comment '수정일시',
    primary key (id)
) engine = innodb
    default charset = utf8mb4
    comment 'POS 카드 실시간 데이터 연동 동의 내역';

create index registration_number_agreement_type
    on pos_easy_connection_agreement_record (registration_number, agreement_type);

create table `pos_sales_card_transactions`
(
    id                              bigint          not null auto_increment,
    no                              bigint          not null comment '번호',
    type                            varchar(255)    not null comment '구분',
    transaction_date_ymd            varchar(255)    not null comment '거래 일자 yyyy-MM-dd',
    transaction_time_hms            varchar(255)    not null comment '거래 시간 HH:mm:ss',
    card_company                    varchar(255)    not null comment '카드사',
    affiliate_card_company          varchar(255)    not null comment '제휴 카드사',
    card_number                     varchar(255)    not null comment '카드 번호',
    approval_number                 bigint          not null comment '승인 번호',
    approval_amount                 varchar(255)    not null comment '승인 금액',
    installment_month               varchar(255)    not null comment '할부 기간',
    registration_number             varchar(255)    not null comment '사업자 등록 번호',
    date_created                    datetime(6)     not null comment '생성일시',
    date_updated                    datetime(6)     not null comment '수정일시',
    data_source                     varchar(255)    not null comment '데이터 출처(CACHE_NOTE/COMMUNITY)',
    primary key (id)
) engine = innodb
    default charset = utf8mb4
    comment 'POS 매출 카드 거래 데이터';

create index transaction_date_ymd_registration_number_data_source
    on pos_sales_card_transactions (transaction_date_ymd, registration_number, data_source);