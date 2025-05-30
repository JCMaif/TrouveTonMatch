CREATE TABLE adresse
(
    id      BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    rue     VARCHAR(255),
    cpostal VARCHAR(255),
    ville   VARCHAR(255),
    CONSTRAINT pk_adresse PRIMARY KEY (id)
);

CREATE TABLE plateforme
(
    id         BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    nom        VARCHAR(255),
    telephone  VARCHAR(255),
    email      VARCHAR(255)                            NOT NULL,
    adresse_id BIGINT,
    CONSTRAINT pk_plateforme PRIMARY KEY (id),
    CONSTRAINT fk_plateforme_on_adresse FOREIGN KEY (adresse_id) REFERENCES adresse (id)
);

CREATE TABLE utilisateur
(
    id              BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    username        VARCHAR(255)                            NOT NULL,
    firstname       VARCHAR(100)                            NOT NULL,
    lastname        VARCHAR(150)                            NOT NULL,
    profile_picture VARCHAR(255),
    password        VARCHAR(255)                            NOT NULL,
    email           VARCHAR(255)                            NOT NULL,
    role            VARCHAR(255),
    adresse_id      BIGINT,
    enabled         BOOLEAN                                 NOT NULL DEFAULT FALSE,
    plateforme_id   BIGINT,
    CONSTRAINT pk_utilisateur PRIMARY KEY (id),
    CONSTRAINT uc_utilisateur_email UNIQUE (email),
    CONSTRAINT uc_utilisateur_username UNIQUE (username),
    CONSTRAINT fk_utilisateur_on_adresse FOREIGN KEY (adresse_id) REFERENCES adresse (id),
    CONSTRAINT fk_utilisateur_on_plateforme FOREIGN KEY (plateforme_id) REFERENCES plateforme (id)
);

CREATE TABLE parrain
(
    id            BIGINT NOT NULL,
    parcours      VARCHAR(255),
    expertise     VARCHAR(255),
    deplacement   VARCHAR(255),
    disponibilite VARCHAR(255),
    max_projects  INTEGER,
    is_active     BOOLEAN,
    CONSTRAINT pk_parrain PRIMARY KEY (id),
    CONSTRAINT fk_parrain_on_id FOREIGN KEY (id) REFERENCES utilisateur (id)
);
CREATE TABLE projet
(
    id            BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    starting_date DATE,
    title         VARCHAR(255),
    description   VARCHAR(255),
    parrain_id    BIGINT,
    porteur_id    BIGINT UNIQUE,
    CONSTRAINT pk_projet PRIMARY KEY (id),
    CONSTRAINT fk_projet_on_parrain FOREIGN KEY (parrain_id) REFERENCES parrain (id),
    CONSTRAINT fk_projet_on_porteur FOREIGN KEY (porteur_id) REFERENCES utilisateur (id)
);
CREATE TABLE porteur
(
    id            BIGINT NOT NULL,
    disponibilite VARCHAR(255),
    projet_id     BIGINT UNIQUE,
    CONSTRAINT pk_porteur PRIMARY KEY (id),
    CONSTRAINT fk_porteur_on_id FOREIGN KEY (id) REFERENCES utilisateur (id),
    CONSTRAINT fk_porteur_on_projet FOREIGN KEY (projet_id) REFERENCES projet (id)
);
