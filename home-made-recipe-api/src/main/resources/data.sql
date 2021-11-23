SET REFERENTIAL_INTEGRITY FALSE;
TRUNCATE TABLE ingredient;
TRUNCATE TABLE recipe;
SET REFERENTIAL_INTEGRITY TRUE;
ALTER TABLE ingredient ALTER COLUMN id RESTART WITH 1;
ALTER TABLE recipe ALTER COLUMN id RESTART WITH 1;

INSERT INTO recipe(id, name, vegetarian, servings, instructions, created_date_time) VALUES (1, 'Erwtensoep', TRUE, 4, 'mix dried split green peas and other vegetables such as celery, onions, leeks, carrots, and potatoes', '2021-11-23 08:30');
INSERT INTO ingredient(id, name, quantity, units, recipe_id) VALUES (1, 'Peas', 400, 'gram', 1);
INSERT INTO ingredient(id, name, quantity, units, recipe_id) VALUES (2, 'Celery', 1, 'piece', 1);
INSERT INTO ingredient(id, name, quantity, units, recipe_id) VALUES (3, 'Onions', 1, 'piece', 1);
INSERT INTO ingredient(id, name, quantity, units, recipe_id) VALUES (4, 'Leeks', 1, 'piece', 1);
INSERT INTO ingredient(id, name, quantity, units, recipe_id) VALUES (5, 'Carrots', 100, 'gram', 1);

INSERT INTO recipe(id, name, vegetarian, servings, instructions, created_date_time) VALUES (2, 'Pannenkoeken', TRUE, 1, 'cook over a pan on high heat and flipped until golden', '2021-11-23 08:30');
INSERT INTO ingredient(id, name, quantity, units, recipe_id) VALUES (6, 'Eggs', 5, 'gram', 2);
INSERT INTO ingredient(id, name, quantity, units, recipe_id) VALUES (7, 'Milk', 1, 'litre', 2);
INSERT INTO ingredient(id, name, quantity, units, recipe_id) VALUES (8, 'Flour', 500, 'gram', 2);

INSERT INTO recipe(id, name, vegetarian, servings, instructions, created_date_time) VALUES (3, 'Poffertjes', TRUE, 5, 'mix with yeast and buckwheat flour, these small, fluffy pancakes', '2021-11-23 08:30');
INSERT INTO ingredient(id, name, quantity, units, recipe_id) VALUES (9, 'Yeast', 5, 'gram', 3);
INSERT INTO ingredient(id, name, quantity, units, recipe_id) VALUES (10, 'Flour', 500, 'gram', 3);
