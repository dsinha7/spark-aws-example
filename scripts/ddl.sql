drop table credit_log;
CREATE TABLE IF NOT EXISTS credit_log
(
   id                           integer PRIMARY KEY,
   loan_amnt                    integer,
   funded_amnt                  integer,
   funded_amnt_inv              integer,
   term                         text,
   int_rate                     text,
   installment                  float8,
   grade                        text,
   sub_grade                    text,
   emp_length                   text,
   home_ownership               text,
   annual_inc                   float8,
   verification_status          text,
   issue_d                      text,
   loan_status                  text,
   description                  text,
   purpose                      text,
   title                        text,
   zip_code                     text,
   addr_state                   text,
   dti                          float8,
   delinq_2yrs                  integer,
   earliest_cr_line             text,
   fico_range_low               integer,
   fico_range_high              integer,
   inq_last_6mths               integer,
   mths_since_last_delinq       integer,
   mths_since_last_record       integer,
   open_acc                     integer,
   pub_rec                      integer,
   revol_bal                    integer,
   revol_util                   text,
   total_acc                    integer,
   initial_list_status          text,
   out_prncp                    integer,
   out_prncp_inv                integer,
   total_pymnt                  float8,
   total_pymnt_inv              float8,
   total_rec_prncp              float8,
   total_rec_int                float8,
   total_rec_late_fee           integer,
   recoveries                   float8,
   collection_recovery_fee      float8,
   last_pymnt_d                 text,
   last_pymnt_amnt              float8,
   next_pymnt_d                 text,
   last_credit_pull_d           text,
   last_fico_range_high         integer,
   last_fico_range_low          integer,
   collections_12_mths_ex_med   integer,
   mths_since_last_major_derog  text,
   policy_code                  integer,
   application_type             text
);

--truncate table credit_log;
--select count(1) from credit_log;


COMMIT;