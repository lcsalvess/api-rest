alter table medicos add ativo tinyint;
update  medicos set ativo = 1;
alter table pacientes add ativo tinyint not null;
update  pacientes set ativo = 1;