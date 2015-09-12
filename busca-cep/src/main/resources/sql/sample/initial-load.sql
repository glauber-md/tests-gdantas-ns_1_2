-- Estados
INSERT INTO estado (sigla,nome) VALUES ('AC','Acre');
INSERT INTO estado (sigla,nome) VALUES ('AL','Alagoas');
INSERT INTO estado (sigla,nome) VALUES ('AM','Amazonas');
INSERT INTO estado (sigla,nome) VALUES ('AP','Amapá');
INSERT INTO estado (sigla,nome) VALUES ('BA','Bahia');
INSERT INTO estado (sigla,nome) VALUES ('CE','Ceará');
INSERT INTO estado (sigla,nome) VALUES ('DF','Distrito Federal');
INSERT INTO estado (sigla,nome) VALUES ('ES','Espírito Santo');
INSERT INTO estado (sigla,nome) VALUES ('GO','Goiás');
INSERT INTO estado (sigla,nome) VALUES ('MA','Maranhão');
INSERT INTO estado (sigla,nome) VALUES ('MG','Minas Gerais');
INSERT INTO estado (sigla,nome) VALUES ('MS','Mato Grosso do Sul');
INSERT INTO estado (sigla,nome) VALUES ('MT','Mato Grosso');
INSERT INTO estado (sigla,nome) VALUES ('PA','Pará');
INSERT INTO estado (sigla,nome) VALUES ('PB','Paraíba');
INSERT INTO estado (sigla,nome) VALUES ('PE','Pernambuco');
INSERT INTO estado (sigla,nome) VALUES ('PI','Piauí');
INSERT INTO estado (sigla,nome) VALUES ('PR','Paraná');
INSERT INTO estado (sigla,nome) VALUES ('RJ','Rio de Janeiro');
INSERT INTO estado (sigla,nome) VALUES ('RN','Rio Grande do Norte');
INSERT INTO estado (sigla,nome) VALUES ('RO','Rondônia');
INSERT INTO estado (sigla,nome) VALUES ('RR','Roraima');
INSERT INTO estado (sigla,nome) VALUES ('RS','Rio Grande do Sul');
INSERT INTO estado (sigla,nome) VALUES ('SC','Santa Catarina');
INSERT INTO estado (sigla,nome) VALUES ('SE','Sergipe');
INSERT INTO estado (sigla,nome) VALUES ('SP','São Paulo');
INSERT INTO estado (sigla,nome) VALUES ('TO','Tocantins');

-- Cidades
INSERT INTO cidade (nome, estadoId) VALUES ('Osasco', (SELECT id FROM estado WHERE sigla = 'SP'));
INSERT INTO cidade (nome, estadoId) VALUES ('São Paulo', (SELECT id FROM estado WHERE sigla = 'SP'));

-- Endereços
INSERT INTO endereco (cep, logradouro, numero, bairro, cidadeId) VALUES ('01234000', 'Rua Islandia', '123', 'Vila Verde', 0);
INSERT INTO endereco (cep, logradouro, numero, bairro, cidadeId) VALUES ('01234001', 'Rua Lisboa', '99', 'Vila dos Remédios', 1);
INSERT INTO endereco (cep, logradouro, numero, bairro, cidadeId) VALUES ('01234002', 'Rua Americana', '199', 'Chácara dos Príncipes', 1);
INSERT INTO endereco (cep, logradouro, numero, bairro, cidadeId) VALUES ('01234003', 'Rua Astrogildo Hermenezigna', '555', 'Vila Royal', 0);