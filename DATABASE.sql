CREATE TABLE users (
    id INT NOT NULL AUTO_INCREMENT,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE recettes (
    id INT NOT NULL AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    temps INT NOT NULL,
    etapes INT NOT NULL,
    image VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE ingredients (
    id INT NOT NULL AUTO_INCREMENT,
    nom VARCHAR(255) NOT NULL,
    type VARCHAR(255) NOT NULL,
    alcool DOUBLE NOT NULL,
    pays VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE etapes (
    id INT NOT NULL AUTO_INCREMENT,
    titre VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    type VARCHAR(255) NOT NULL,
    quantite DOUBLE NOT NULL,
    temps INT NOT NULL,
    recette_id INT NOT NULL,
    ingredient_id INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (recette_id) REFERENCES recettes(id)
);

CREATE TABLE categories (
    id INT NOT NULL AUTO_INCREMENT,
    nom VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE recettes_categories (
    id INT NOT NULL AUTO_INCREMENT,
    recette_id INT NOT NULL,
    categorie_id INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (recette_id) REFERENCES recettes(id),
    FOREIGN KEY (categorie_id) REFERENCES categories(id)
);

CREATE TABLE favoris (
    id INT NOT NULL AUTO_INCREMENT,
    user_id INT NOT NULL,
    recette_id INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (recette_id) REFERENCES recettes(id)
);

CREATE TABLE recettes_ingredients (
    id INT NOT NULL AUTO_INCREMENT,
    recette_id INT NOT NULL,
    ingredient_id INT NOT NULL,
    quantite DOUBLE NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (recette_id) REFERENCES recettes(id),
    FOREIGN KEY (ingredient_id) REFERENCES ingredients(id)
);