-- Tabelul clienților firmei de contabilitate
CREATE TABLE clienti (
    id SERIAL PRIMARY KEY,
    nume VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE,
    telefon VARCHAR(20),
    data_inregistrare DATE DEFAULT CURRENT_DATE,
    tip_client VARCHAR(20) CHECK (tip_client IN ('persoana_fizica', 'firma'))
);

-- Adrese clienți
CREATE TABLE adrese (
    id SERIAL PRIMARY KEY,
    client_id INT REFERENCES clienti(id) ON DELETE CASCADE,
    strada VARCHAR(150),
    oras VARCHAR(100),
    cod_postal VARCHAR(20),
    tara VARCHAR(50)
);

-- Contabili
CREATE TABLE contabili (
    id SERIAL PRIMARY KEY,
    nume VARCHAR(100),
    email VARCHAR(100) UNIQUE,
    specializare VARCHAR(100)
);

-- Servicii oferite
CREATE TABLE servicii (
    id SERIAL PRIMARY KEY,
    nume VARCHAR(100),
    descriere TEXT,
    tarif_lunar NUMERIC(10,2)
);

-- Contracte semnate cu clienți
CREATE TABLE contracte (
    id SERIAL PRIMARY KEY,
    client_id INT REFERENCES clienti(id),
    contabil_id INT REFERENCES contabili(id),
    data_start DATE NOT NULL,
    data_sfarsit DATE,
    activ BOOLEAN DEFAULT TRUE
);

-- Servicii incluse în fiecare contract
CREATE TABLE contracte_servicii (
    id SERIAL PRIMARY KEY,
    contract_id INT REFERENCES contracte(id) ON DELETE CASCADE,
    serviciu_id INT REFERENCES servicii(id)
);

-- Facturi
CREATE TABLE facturi (
    id SERIAL PRIMARY KEY,
    client_id INT REFERENCES clienti(id),
    data_emiterii DATE DEFAULT CURRENT_DATE,
    data_scadenta DATE,
    total NUMERIC(10,2)
);

-- Linii factură
CREATE TABLE facturi_linii (
    id SERIAL PRIMARY KEY,
    factura_id INT REFERENCES facturi(id) ON DELETE CASCADE,
    serviciu_id INT REFERENCES servicii(id),
    cantitate INT DEFAULT 1,
    pret_unitar NUMERIC(10,2)
);

-- Plăți ale facturilor
CREATE TABLE plati (
    id SERIAL PRIMARY KEY,
    factura_id INT REFERENCES facturi(id) ON DELETE CASCADE,
    data_platii DATE,
    suma NUMERIC(10,2),
    metoda VARCHAR(50)
);

-- Documente contabile încărcate
CREATE TABLE documente (
    id SERIAL PRIMARY KEY,
    client_id INT REFERENCES clienti(id),
    tip_document VARCHAR(100),
    data_document DATE,
    fisier VARCHAR(255)
);
