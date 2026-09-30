TRUNCATE TABLE master_products RESTART IDENTITY CASCADE;

INSERT INTO master_products (name, category, image_url, default_unit) VALUES
-- ==========================================
-- Nabiał, Jajka i Alternatywy (Dairy & Eggs)
-- ==========================================
('Milk', 'Dairy', 'https://spoonacular.com/cdn/ingredients_100x100/milk.png', 'L'),
('Egg', 'Dairy', 'https://spoonacular.com/cdn/ingredients_100x100/egg.png', 'PIECE'),
('Butter', 'Dairy', 'https://spoonacular.com/cdn/ingredients_100x100/butter-sliced.jpg', 'PACK'),
('Cheese', 'Dairy', 'https://spoonacular.com/cdn/ingredients_100x100/cheddar-cheese.png', 'KG'),
('Yogurt', 'Dairy', 'https://spoonacular.com/cdn/ingredients_100x100/plain-yogurt.jpg', 'PACK'),
('Cream', 'Dairy', 'https://spoonacular.com/cdn/ingredients_100x100/fluid-cream.jpg', 'ML'),
('Sour Cream', 'Dairy', 'https://spoonacular.com/cdn/ingredients_100x100/sour-cream.jpg', 'PACK'),
('Cream Cheese', 'Dairy', 'https://spoonacular.com/cdn/ingredients_100x100/cream-cheese.jpg', 'PACK'),
('Mozzarella', 'Dairy', 'https://spoonacular.com/cdn/ingredients_100x100/mozzarella.png', 'PIECE'),
('Feta Cheese', 'Dairy', 'https://spoonacular.com/cdn/ingredients_100x100/feta.png', 'PACK'),
('Ricotta', 'Dairy', 'https://spoonacular.com/cdn/ingredients_100x100/ricotta.png', 'PACK'),
('Parmesan', 'Dairy', 'https://spoonacular.com/cdn/ingredients_100x100/parmesan.jpg', 'G'),
('Blue Cheese', 'Dairy', 'https://spoonacular.com/cdn/ingredients_100x100/blue-cheese.png', 'G'),
('Brie', 'Dairy', 'https://spoonacular.com/cdn/ingredients_100x100/brie.jpg', 'PACK'),
('Condensed Milk', 'Dairy', 'https://spoonacular.com/cdn/ingredients_100x100/condensed-milk.jpg', 'PACK'),
('Ghee', 'Dairy', 'https://spoonacular.com/cdn/ingredients_100x100/ghee.jpg', 'ML'),
('Almond Milk', 'Dairy', 'https://spoonacular.com/cdn/ingredients_100x100/almond-milk.png', 'L'),
('Soy Milk', 'Dairy', 'https://spoonacular.com/cdn/ingredients_100x100/soy-milk.jpg', 'L'),
('Oat Milk', 'Dairy', 'https://spoonacular.com/cdn/ingredients_100x100/oat-milk.png', 'L'),

-- ==========================================
-- Mięso i Drób (Meat & Poultry)
-- ==========================================
('Chicken Breast', 'Meat', 'https://spoonacular.com/cdn/ingredients_100x100/chicken-breasts.png', 'KG'),
('Chicken Thighs', 'Meat', 'https://spoonacular.com/cdn/ingredients_100x100/chicken-thighs.png', 'KG'),
('Beef', 'Meat', 'https://spoonacular.com/cdn/ingredients_100x100/beef-cubes-raw.png', 'KG'),
('Ground Beef', 'Meat', 'https://spoonacular.com/cdn/ingredients_100x100/fresh-ground-beef.jpg', 'KG'),
('Pork', 'Meat', 'https://spoonacular.com/cdn/ingredients_100x100/pork-tenderloin-raw.png', 'KG'),
('Pork Chops', 'Meat', 'https://spoonacular.com/cdn/ingredients_100x100/pork-chops.jpg', 'KG'),
('Bacon', 'Meat', 'https://spoonacular.com/cdn/ingredients_100x100/raw-bacon.png', 'PACK'),
('Sausage', 'Meat', 'https://spoonacular.com/cdn/ingredients_100x100/raw-pork-sausage.png', 'PACK'),
('Ham', 'Meat', 'https://spoonacular.com/cdn/ingredients_100x100/ham-whole.jpg', 'KG'),
('Turkey Breast', 'Meat', 'https://spoonacular.com/cdn/ingredients_100x100/turkey-breast.jpg', 'KG'),
('Ground Turkey', 'Meat', 'https://spoonacular.com/cdn/ingredients_100x100/meat-ground.jpg', 'KG'),
('Lamb', 'Meat', 'https://spoonacular.com/cdn/ingredients_100x100/lamb-shanks.jpg', 'KG'),
('Prosciutto', 'Meat', 'https://spoonacular.com/cdn/ingredients_100x100/prosciutto.jpg', 'PACK'),
('Salami', 'Meat', 'https://spoonacular.com/cdn/ingredients_100x100/salami.jpg', 'PACK'),
('Pepperoni', 'Meat', 'https://spoonacular.com/cdn/ingredients_100x100/pepperoni.png', 'PACK'),

-- ==========================================
-- Ryby i Owoce Morza (Seafood)
-- ==========================================
('Salmon', 'Seafood', 'https://spoonacular.com/cdn/ingredients_100x100/salmon.png', 'KG'),
('Tuna', 'Seafood', 'https://spoonacular.com/cdn/ingredients_100x100/canned-tuna.png', 'PACK'),
('Shrimp', 'Seafood', 'https://spoonacular.com/cdn/ingredients_100x100/shrimp.png', 'G'),
('Cod', 'Seafood', 'https://spoonacular.com/cdn/ingredients_100x100/cod-fillet.jpg', 'KG'),
('Tilapia', 'Seafood', 'https://spoonacular.com/cdn/ingredients_100x100/raw-tilapia.jpg', 'KG'),
('Scallops', 'Seafood', 'https://spoonacular.com/cdn/ingredients_100x100/scallops.jpg', 'G'),
('Crab Meat', 'Seafood', 'https://spoonacular.com/cdn/ingredients_100x100/crabmeat.jpg', 'PACK'),
('Mussels', 'Seafood', 'https://spoonacular.com/cdn/ingredients_100x100/mussels.jpg', 'KG'),

-- ==========================================
-- Warzywa (Vegetables)
-- ==========================================
('Tomato', 'Vegetables', 'https://spoonacular.com/cdn/ingredients_100x100/tomato.png', 'PIECE'),
('Onion', 'Vegetables', 'https://spoonacular.com/cdn/ingredients_100x100/brown-onion.png', 'PIECE'),
('Garlic', 'Vegetables', 'https://spoonacular.com/cdn/ingredients_100x100/garlic.png', 'PIECE'),
('Potato', 'Vegetables', 'https://spoonacular.com/cdn/ingredients_100x100/potatoes-yukon-gold.png', 'KG'),
('Carrot', 'Vegetables', 'https://spoonacular.com/cdn/ingredients_100x100/sliced-carrot.png', 'PIECE'),
('Broccoli', 'Vegetables', 'https://spoonacular.com/cdn/ingredients_100x100/broccoli.jpg', 'PIECE'),
('Spinach', 'Vegetables', 'https://spoonacular.com/cdn/ingredients_100x100/spinach.jpg', 'PACK'),
('Bell Pepper', 'Vegetables', 'https://spoonacular.com/cdn/ingredients_100x100/red-pepper.jpg', 'PIECE'),
('Cucumber', 'Vegetables', 'https://spoonacular.com/cdn/ingredients_100x100/cucumber.jpg', 'PIECE'),
('Mushroom', 'Vegetables', 'https://spoonacular.com/cdn/ingredients_100x100/mushrooms.png', 'G'),
('Lettuce', 'Vegetables', 'https://spoonacular.com/cdn/ingredients_100x100/iceberg-lettuce.jpg', 'PIECE'),
('Zucchini', 'Vegetables', 'https://spoonacular.com/cdn/ingredients_100x100/zucchini.jpg', 'PIECE'),
('Celery', 'Vegetables', 'https://spoonacular.com/cdn/ingredients_100x100/celery.jpg', 'PIECE'),
('Asparagus', 'Vegetables', 'https://spoonacular.com/cdn/ingredients_100x100/asparagus.png', 'PACK'),
('Cabbage', 'Vegetables', 'https://spoonacular.com/cdn/ingredients_100x100/cabbage.jpg', 'PIECE'),
('Cauliflower', 'Vegetables', 'https://spoonacular.com/cdn/ingredients_100x100/cauliflower.jpg', 'PIECE'),
('Sweet Potato', 'Vegetables', 'https://spoonacular.com/cdn/ingredients_100x100/sweet-potato.png', 'PIECE'),
('Green Beans', 'Vegetables', 'https://spoonacular.com/cdn/ingredients_100x100/green-beans-or-string-beans.jpg', 'G'),
('Peas', 'Vegetables', 'https://spoonacular.com/cdn/ingredients_100x100/peas.jpg', 'PACK'),
('Corn', 'Vegetables', 'https://spoonacular.com/cdn/ingredients_100x100/corn.png', 'PIECE'),
('Eggplant', 'Vegetables', 'https://spoonacular.com/cdn/ingredients_100x100/eggplant.png', 'PIECE'),
('Radish', 'Vegetables', 'https://spoonacular.com/cdn/ingredients_100x100/radish.jpg', 'PIECE'),
('Leek', 'Vegetables', 'https://spoonacular.com/cdn/ingredients_100x100/leeks.jpg', 'PIECE'),
('Brussels Sprouts', 'Vegetables', 'https://spoonacular.com/cdn/ingredients_100x100/brussels-sprouts.jpg', 'G'),
('Pumpkin', 'Vegetables', 'https://spoonacular.com/cdn/ingredients_100x100/pumpkin.png', 'PIECE'),
('Butternut Squash', 'Vegetables', 'https://spoonacular.com/cdn/ingredients_100x100/butternut-squash.jpg', 'PIECE'),
('Kale', 'Vegetables', 'https://spoonacular.com/cdn/ingredients_100x100/kale.jpg', 'PACK'),
('Jalapeno', 'Vegetables', 'https://spoonacular.com/cdn/ingredients_100x100/jalapeno-pepper.png', 'PIECE'),
('Scallions', 'Vegetables', 'https://spoonacular.com/cdn/ingredients_100x100/spring-onions.jpg', 'PIECE'),
('Shallot', 'Vegetables', 'https://spoonacular.com/cdn/ingredients_100x100/shallots.jpg', 'PIECE'),

-- ==========================================
-- Owoce (Fruits)
-- ==========================================
('Apple', 'Fruits', 'https://spoonacular.com/cdn/ingredients_100x100/apple.jpg', 'PIECE'),
('Banana', 'Fruits', 'https://spoonacular.com/cdn/ingredients_100x100/bananas.jpg', 'PIECE'),
('Lemon', 'Fruits', 'https://spoonacular.com/cdn/ingredients_100x100/lemon.png', 'PIECE'),
('Orange', 'Fruits', 'https://spoonacular.com/cdn/ingredients_100x100/orange.png', 'PIECE'),
('Strawberry', 'Fruits', 'https://spoonacular.com/cdn/ingredients_100x100/strawberries.png', 'PACK'),
('Blueberry', 'Fruits', 'https://spoonacular.com/cdn/ingredients_100x100/blueberries.jpg', 'PACK'),
('Avocado', 'Fruits', 'https://spoonacular.com/cdn/ingredients_100x100/avocado.jpg', 'PIECE'),
('Pineapple', 'Fruits', 'https://spoonacular.com/cdn/ingredients_100x100/pineapple.jpg', 'PIECE'),
('Mango', 'Fruits', 'https://spoonacular.com/cdn/ingredients_100x100/mango.jpg', 'PIECE'),
('Peach', 'Fruits', 'https://spoonacular.com/cdn/ingredients_100x100/peaches.jpg', 'PIECE'),
('Plum', 'Fruits', 'https://spoonacular.com/cdn/ingredients_100x100/plum.jpg', 'PIECE'),
('Cherry', 'Fruits', 'https://spoonacular.com/cdn/ingredients_100x100/cherries.jpg', 'KG'),
('Grapes', 'Fruits', 'https://spoonacular.com/cdn/ingredients_100x100/red-grapes.jpg', 'KG'),
('Watermelon', 'Fruits', 'https://spoonacular.com/cdn/ingredients_100x100/watermelon.png', 'PIECE'),
('Kiwi', 'Fruits', 'https://spoonacular.com/cdn/ingredients_100x100/kiwis.jpg', 'PIECE'),
('Pear', 'Fruits', 'https://spoonacular.com/cdn/ingredients_100x100/pears-bosc.jpg', 'PIECE'),
('Pomegranate', 'Fruits', 'https://spoonacular.com/cdn/ingredients_100x100/pomegranate.jpg', 'PIECE'),
('Raspberry', 'Fruits', 'https://spoonacular.com/cdn/ingredients_100x100/raspberries.jpg', 'PACK'),
('Blackberry', 'Fruits', 'https://spoonacular.com/cdn/ingredients_100x100/blackberries.jpg', 'PACK'),
('Grapefruit', 'Fruits', 'https://spoonacular.com/cdn/ingredients_100x100/grapefruit.jpg', 'PIECE'),
('Lime', 'Fruits', 'https://spoonacular.com/cdn/ingredients_100x100/lime.jpg', 'PIECE'),
('Coconut', 'Fruits', 'https://spoonacular.com/cdn/ingredients_100x100/coconut.jpg', 'PIECE'),

-- ==========================================
-- Produkty Sypkie, Zboża i Węglowodany (Grains)
-- ==========================================
('Pasta', 'Grains', 'https://spoonacular.com/cdn/ingredients_100x100/fusilli.jpg', 'PACK'),
('Spaghetti', 'Grains', 'https://spoonacular.com/cdn/ingredients_100x100/spaghetti.jpg', 'PACK'),
('Penne', 'Grains', 'https://spoonacular.com/cdn/ingredients_100x100/penne-pasta.jpg', 'PACK'),
('Macaroni', 'Grains', 'https://spoonacular.com/cdn/ingredients_100x100/macaroni.png', 'PACK'),
('Noodles', 'Grains', 'https://spoonacular.com/cdn/ingredients_100x100/egg-noodles.jpg', 'PACK'),
('Rice', 'Grains', 'https://spoonacular.com/cdn/ingredients_100x100/uncooked-white-rice.png', 'KG'),
('Brown Rice', 'Grains', 'https://spoonacular.com/cdn/ingredients_100x100/brown-rice.png', 'KG'),
('Quinoa', 'Grains', 'https://spoonacular.com/cdn/ingredients_100x100/quinoa.png', 'PACK'),
('Couscous', 'Grains', 'https://spoonacular.com/cdn/ingredients_100x100/couscous-cooked.jpg', 'PACK'),
('Flour', 'Grains', 'https://spoonacular.com/cdn/ingredients_100x100/flour.png', 'KG'),
('Bread', 'Grains', 'https://spoonacular.com/cdn/ingredients_100x100/white-bread.jpg', 'PIECE'),
('Tortilla', 'Grains', 'https://spoonacular.com/cdn/ingredients_100x100/flour-tortilla.jpg', 'PACK'),
('Pita Bread', 'Grains', 'https://spoonacular.com/cdn/ingredients_100x100/pita-bread.jpg', 'PACK'),
('Oats', 'Grains', 'https://spoonacular.com/cdn/ingredients_100x100/rolled-oats.jpg', 'PACK'),
('Cornmeal', 'Grains', 'https://spoonacular.com/cdn/ingredients_100x100/cornmeal.png', 'PACK'),

-- ==========================================
-- Strączkowe (Legumes)
-- ==========================================
('Chickpeas', 'Legumes', 'https://spoonacular.com/cdn/ingredients_100x100/chickpeas.png', 'PACK'),
('Black Beans', 'Legumes', 'https://spoonacular.com/cdn/ingredients_100x100/black-beans.jpg', 'PACK'),
('Kidney Beans', 'Legumes', 'https://spoonacular.com/cdn/ingredients_100x100/kidney-beans.jpg', 'PACK'),
('Lentils', 'Legumes', 'https://spoonacular.com/cdn/ingredients_100x100/lentils-brown.jpg', 'PACK'),
('Pinto Beans', 'Legumes', 'https://spoonacular.com/cdn/ingredients_100x100/pinto-beans.jpg', 'PACK'),
('Edamame', 'Legumes', 'https://spoonacular.com/cdn/ingredients_100x100/edamame.png', 'PACK'),

-- ==========================================
-- Orzechy i Nasiona (Nuts & Seeds)
-- ==========================================
('Peanut Butter', 'Pantry', 'https://spoonacular.com/cdn/ingredients_100x100/peanut-butter.png', 'PACK'),
('Peanuts', 'Nuts', 'https://spoonacular.com/cdn/ingredients_100x100/peanuts.png', 'PACK'),
('Almonds', 'Nuts', 'https://spoonacular.com/cdn/ingredients_100x100/almonds.jpg', 'PACK'),
('Walnuts', 'Nuts', 'https://spoonacular.com/cdn/ingredients_100x100/walnuts.jpg', 'PACK'),
('Cashews', 'Nuts', 'https://spoonacular.com/cdn/ingredients_100x100/cashews.jpg', 'PACK'),
('Pecans', 'Nuts', 'https://spoonacular.com/cdn/ingredients_100x100/pecans.jpg', 'PACK'),
('Pistachios', 'Nuts', 'https://spoonacular.com/cdn/ingredients_100x100/pistachios.jpg', 'PACK'),
('Sunflower Seeds', 'Seeds', 'https://spoonacular.com/cdn/ingredients_100x100/sunflower-seeds.jpg', 'PACK'),
('Pumpkin Seeds', 'Seeds', 'https://spoonacular.com/cdn/ingredients_100x100/pumpkin-seeds.jpg', 'PACK'),
('Chia Seeds', 'Seeds', 'https://spoonacular.com/cdn/ingredients_100x100/chia-seeds.jpg', 'PACK'),
('Sesame Seeds', 'Seeds', 'https://spoonacular.com/cdn/ingredients_100x100/sesame-seeds.png', 'PACK'),

-- ==========================================
-- Oleje, Sosy, Buliony (Pantry & Liquids)
-- ==========================================
('Olive Oil', 'Pantry', 'https://spoonacular.com/cdn/ingredients_100x100/olive-oil.jpg', 'L'),
('Vegetable Oil', 'Pantry', 'https://spoonacular.com/cdn/ingredients_100x100/vegetable-oil.jpg', 'L'),
('Sesame Oil', 'Pantry', 'https://spoonacular.com/cdn/ingredients_100x100/sesame-oil.png', 'ML'),
('Chicken Broth', 'Pantry', 'https://spoonacular.com/cdn/ingredients_100x100/chicken-broth.png', 'L'),
('Beef Broth', 'Pantry', 'https://spoonacular.com/cdn/ingredients_100x100/beef-broth.png', 'L'),
('Vegetable Broth', 'Pantry', 'https://spoonacular.com/cdn/ingredients_100x100/broth.jpg', 'L'),
('Canned Tomatoes', 'Pantry', 'https://spoonacular.com/cdn/ingredients_100x100/tomatoes-canned.png', 'PACK'),
('Tomato Paste', 'Pantry', 'https://spoonacular.com/cdn/ingredients_100x100/tomato-paste.jpg', 'PACK'),
('Coconut Milk Canned', 'Pantry', 'https://spoonacular.com/cdn/ingredients_100x100/coconut-milk.png', 'PACK'),
('Soy Sauce', 'Pantry', 'https://spoonacular.com/cdn/ingredients_100x100/soy-sauce.jpg', 'ML'),
('Mustard', 'Pantry', 'https://spoonacular.com/cdn/ingredients_100x100/regular-mustard.jpg', 'PACK'),
('Ketchup', 'Pantry', 'https://spoonacular.com/cdn/ingredients_100x100/ketchup.png', 'PACK'),
('Mayonnaise', 'Pantry', 'https://spoonacular.com/cdn/ingredients_100x100/mayonnaise.png', 'PACK'),
('Vinegar', 'Pantry', 'https://spoonacular.com/cdn/ingredients_100x100/vinegar-(white).jpg', 'L'),
('Apple Cider Vinegar', 'Pantry', 'https://spoonacular.com/cdn/ingredients_100x100/apple-cider-vinegar.jpg', 'ML'),
('Balsamic Vinegar', 'Pantry', 'https://spoonacular.com/cdn/ingredients_100x100/balsamic-vinegar.jpg', 'ML'),
('Sriracha', 'Pantry', 'https://spoonacular.com/cdn/ingredients_100x100/sriracha.png', 'ML'),
('Barbecue Sauce', 'Pantry', 'https://spoonacular.com/cdn/ingredients_100x100/barbecue-sauce.jpg', 'ML'),

-- ==========================================
-- Przyprawy, Zioła i Do Pieczenia (Spices & Baking)
-- ==========================================
('Salt', 'Spices', 'https://spoonacular.com/cdn/ingredients_100x100/salt.jpg', 'KG'),
('Black Pepper', 'Spices', 'https://spoonacular.com/cdn/ingredients_100x100/pepper.jpg', 'G'),
('Sugar', 'Baking', 'https://spoonacular.com/cdn/ingredients_100x100/sugar-in-bowl.png', 'KG'),
('Brown Sugar', 'Baking', 'https://spoonacular.com/cdn/ingredients_100x100/dark-brown-sugar.png', 'KG'),
('Honey', 'Pantry', 'https://spoonacular.com/cdn/ingredients_100x100/honey.png', 'ML'),
('Maple Syrup', 'Pantry', 'https://spoonacular.com/cdn/ingredients_100x100/maple-syrup.png', 'ML'),
('Baking Powder', 'Baking', 'https://spoonacular.com/cdn/ingredients_100x100/white-powder.jpg', 'PACK'),
('Baking Soda', 'Baking', 'https://spoonacular.com/cdn/ingredients_100x100/white-powder.jpg', 'PACK'),
('Yeast', 'Baking', 'https://spoonacular.com/cdn/ingredients_100x100/yeast-granules.jpg', 'PACK'),
('Cornstarch', 'Baking', 'https://spoonacular.com/cdn/ingredients_100x100/white-powder.jpg', 'PACK'),
('Cocoa Powder', 'Baking', 'https://spoonacular.com/cdn/ingredients_100x100/cocoa-powder.png', 'PACK'),
('Chocolate Chips', 'Baking', 'https://spoonacular.com/cdn/ingredients_100x100/chocolate-chips.jpg', 'PACK'),
('Vanilla Extract', 'Baking', 'https://spoonacular.com/cdn/ingredients_100x100/vanilla-extract.jpg', 'ML'),
('Cumin', 'Spices', 'https://spoonacular.com/cdn/ingredients_100x100/ground-cumin.jpg', 'G'),
('Paprika', 'Spices', 'https://spoonacular.com/cdn/ingredients_100x100/paprika.jpg', 'G'),
('Cinnamon', 'Spices', 'https://spoonacular.com/cdn/ingredients_100x100/cinnamon.jpg', 'G'),
('Oregano', 'Spices', 'https://spoonacular.com/cdn/ingredients_100x100/oregano.jpg', 'G'),
('Basil', 'Spices', 'https://spoonacular.com/cdn/ingredients_100x100/basil.jpg', 'G'),
('Thyme', 'Spices', 'https://spoonacular.com/cdn/ingredients_100x100/thyme.jpg', 'G'),
('Rosemary', 'Spices', 'https://spoonacular.com/cdn/ingredients_100x100/rosemary.jpg', 'G'),
('Chili Powder', 'Spices', 'https://spoonacular.com/cdn/ingredients_100x100/chili-powder.jpg', 'G'),
('Curry Powder', 'Spices', 'https://spoonacular.com/cdn/ingredients_100x100/curry-powder.jpg', 'G'),
('Turmeric', 'Spices', 'https://spoonacular.com/cdn/ingredients_100x100/turmeric.jpg', 'G'),
('Nutmeg', 'Spices', 'https://spoonacular.com/cdn/ingredients_100x100/ground-nutmeg.jpg', 'G'),
('Ginger', 'Spices', 'https://spoonacular.com/cdn/ingredients_100x100/ginger.png', 'G'),
('Coriander', 'Spices', 'https://spoonacular.com/cdn/ingredients_100x100/ground-coriander.jpg', 'G'),
('Dill', 'Spices', 'https://spoonacular.com/cdn/ingredients_100x100/dill.jpg', 'G'),
('Parsley', 'Spices', 'https://spoonacular.com/cdn/ingredients_100x100/parsley.jpg', 'G');