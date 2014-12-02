create index charge_bts_type on wy_bts_charge(bts_type);
create index charge_cost_type on wy_bts_charge(cost_type);
create index charge_contract_endtime on wy_bts_charge(contract_endtime);

create index charge_list_int_id on wy_bts_charge_list(int_id);
create index charge_list_cost_type on wy_bts_charge_list(cost_type);
create index charge_list_bts_id on wy_bts_charge_list(bts_id);
create index charge_list_country on wy_bts_charge_list(country_id);
create index charge_list_bsc_name on wy_bts_charge_list(bsc_name);
create index charge_list_bts_type on wy_bts_charge_list(bts_type);

