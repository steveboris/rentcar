DROP TABLE IF EXISTS kunden;
DROP TABLE IF EXISTS fahrzeuge;
DROP TABLE IF EXISTS bestellungen;

CREATE TABLE kunden
(
    KundeID INTEGER NOT NULL AUTO_INCREMENT,
    Name    VARCHAR(40) NOT NULL,
    Vorname VARCHAR(40) NOT NULL,
    Geburstsdatum VARCHAR(10) NOT NULL,
    Adresse VARCHAR(40) NOT NULL,
    Ort VARCHAR(40) NOT NULL,
    Postleitzahl INTEGER NOT NULL,
    AnmeldungsDatum VARCHAR(30) NOT NULL,
    PRIMARY KEY (KundeID)
);

CREATE TABLE fahrzeuge
(
    FahrzeugID INTEGER NOT NULL AUTO_INCREMENT,
    KaufsDatum VARCHAR(10) NOT NULL,
    Kennzeichnen VARCHAR(20) NOT NULL,
    Farbe VARCHAR(20) NOT NULL,
    Hersteller VARCHAR(20) NOT NULL,
    Model VARCHAR(20) NOT NULL,
    Typ VARCHAR(40) NOT NULL,
    Preis DECIMAL(5,2) NOT NULL,
    TankVolume SMALLINT NOT NULL,
    Geschwindigkeit SMALLINT NOT NULL,
    VersicherungsNr VARCHAR(40) NOT NULL,
    Tuev VARCHAR(40) NOT NULL,
    PRIMARY KEY (FahrzeugID)
);

CREATE TABLE bestellungen
(
    BestellungsID INTEGER NOT NULL AUTO_INCREMENT,
    FahrzeugID INTEGER NOT NULL,
    KundeID INTEGER NOT NULL,
    AbholungsDatum VARCHAR(20) NOT NULL,
   	RueckgabeDatum VARCHAR(20) NOT NULL,
   	MietCaution DECIMAL(5,2) NOT NULL,
   	MietKosten DECIMAL(7,2),
    PRIMARY KEY (BestellungsID),
    FOREIGN KEY (FahrzeugID) 
    	REFERENCES fahrzeuge (FahrzeugID) 
    	ON DELETE CASCADE 
    	ON UPDATE CASCADE,
    FOREIGN KEY (KundeID)
    	REFERENCES kunden (KundeID) 
    	ON DELETE CASCADE 
    	ON UPDATE CASCADE
);


DROP TRIGGER IF EXISTS kunde_join_on_date;
DELIMITER //
CREATE TRIGGER kunde_join_on_date
BEFORE INSERT ON kunden FOR EACH ROW
BEGIN
    IF (NEW.AnmeldungsDatum  IS NULL) THEN
        SET NEW.AnmeldungsDatum = now();
    END IF;
END//
