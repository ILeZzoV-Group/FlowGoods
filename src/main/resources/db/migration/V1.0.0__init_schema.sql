CREATE TABLE IF NOT EXISTS users (
    id bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    uuid uuid DEFAULT gen_random_uuid() NOT NULL UNIQUE,
    email varchar(255) NOT NULL,
    password_hash varchar(255) NOT NULL,
    created_at timestamptz DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at timestamptz DEFAULT CURRENT_TIMESTAMP NOT NULL,
    version INT NOT NULL DEFAULT 0
);

CREATE UNIQUE INDEX uk_users_email_lower
    ON users (LOWER(email));

CREATE TABLE IF NOT EXISTS profiles (
    user_id bigint PRIMARY KEY,
    username varchar(63) NOT NULL,
    first_name varchar(63),
    second_name varchar(63),
    avatar_url text,
    version INT NOT NULL DEFAULT 0,
    CONSTRAINT fk_profile_user FOREIGN KEY (user_id)
        REFERENCES users (id) ON DELETE CASCADE
);

CREATE UNIQUE INDEX uk_profiles_username_lower
    ON profiles (LOWER(username));

CREATE TABLE IF NOT EXISTS workspaces (
    id bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    uuid uuid DEFAULT gen_random_uuid() NOT NULL UNIQUE,
    name varchar(63) NOT NULL,
    owner_id bigint NOT NULL,
    created_at timestamptz DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at timestamptz DEFAULT CURRENT_TIMESTAMP NOT NULL,
    version INT NOT NULL DEFAULT 0,

    CONSTRAINT fk_workspaces_owner FOREIGN KEY (owner_id)
        REFERENCES users(id) ON DELETE CASCADE
);

CREATE UNIQUE INDEX uk_workspaces_owner_id_name_lower
    ON workspaces (owner_id, LOWER(name));

CREATE INDEX idx_workspaces_owner_id_id
    ON workspaces (owner_id, id);

CREATE TABLE IF NOT EXISTS suppliers (
    id bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    uuid uuid DEFAULT gen_random_uuid() NOT NULL UNIQUE,
    name varchar(63) NOT NULL,
    workspace_id bigint NOT NULL,
    created_at timestamptz DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at timestamptz DEFAULT CURRENT_TIMESTAMP NOT NULL,
    version INT NOT NULL DEFAULT 0,

    CONSTRAINT fk_suppliers_workspace FOREIGN KEY (workspace_id)
        REFERENCES workspaces(id) ON DELETE CASCADE
);

CREATE INDEX idx_suppliers_owner_id_id
    ON suppliers (workspace_id, id);

CREATE TABLE IF NOT EXISTS contacts (
    supplier_id BIGINT PRIMARY KEY,
    phone varchar(15),
    email varchar(255),
    link text,
    version INT NOT NULL DEFAULT 0,
    CONSTRAINT fk_contact_supplier FOREIGN KEY (supplier_id)
        REFERENCES suppliers (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS categories (
    id bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    uuid uuid DEFAULT gen_random_uuid() NOT NULL UNIQUE,
    name varchar(63) NOT NULL,
    description text,
    workspace_id bigint NOT NULL,
    created_at timestamptz DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at timestamptz DEFAULT CURRENT_TIMESTAMP NOT NULL,
    version INT NOT NULL DEFAULT 0,

    CONSTRAINT fk_categories_workspace FOREIGN KEY (workspace_id)
        REFERENCES workspaces(id) ON DELETE CASCADE
);

CREATE UNIQUE INDEX uk_categories_workspace_id_name_lower
    ON categories (workspace_id, LOWER(name));

CREATE INDEX idx_categories_owner_id_id
    ON categories (workspace_id, id);

CREATE TABLE IF NOT EXISTS marketplaces (
    id bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    uuid uuid DEFAULT gen_random_uuid() NOT NULL UNIQUE,
    name varchar(63) NOT NULL,
    url text NOT NULL,
    workspace_id bigint NOT NULL,
    created_at timestamptz DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at timestamptz DEFAULT CURRENT_TIMESTAMP NOT NULL,
    version INT NOT NULL DEFAULT 0,

    CONSTRAINT fk_marketplaces_workspace FOREIGN KEY (workspace_id)
        REFERENCES workspaces(id) ON DELETE CASCADE
);

CREATE UNIQUE INDEX uk_marketplaces_workspace_id_name_lower
    ON marketplaces (workspace_id, LOWER(name));

CREATE INDEX idx_marketplaces_owner_id_id
    ON marketplaces (workspace_id, id);

CREATE TABLE IF NOT EXISTS products (
    id bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    uuid uuid DEFAULT gen_random_uuid() NOT NULL UNIQUE,
    name varchar(255) NOT NULL,
    sku varchar(63),
    status varchar(63) NOT NULL DEFAULT 'DRAFT' CHECK ( status in ('DRAFT', 'ACTIVE', 'INACTIVE', 'ARCHIVED') ),
    price decimal(12, 2) CHECK ( price >= 0 ),
    category_id bigint REFERENCES categories(id),
    marketplace_id bigint REFERENCES marketplaces(id),
    supplier_id bigint REFERENCES suppliers(id),
    workspace_id bigint REFERENCES workspaces(id) NOT NULL,
    created_at timestamptz DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at timestamptz DEFAULT CURRENT_TIMESTAMP NOT NULL,
    version INT NOT NULL DEFAULT 0,

    CONSTRAINT fk_products_workspace FOREIGN KEY (workspace_id)
        REFERENCES workspaces(id) ON DELETE CASCADE
);

CREATE INDEX idx_products_workspace_id_id
    ON products (workspace_id, id);

CREATE UNIQUE INDEX uk_products_workspace_id_sku_lower
    ON products (workspace_id, LOWER(sku)) WHERE sku IS NOT NULL;

CREATE INDEX uk_products_workspace_id_status
    ON products (workspace_id, status);

CREATE INDEX uk_products_workspace_id_category
    ON products (workspace_id, category_id);

CREATE INDEX idx_products_workspace_id_marketplace
    ON products (workspace_id, marketplace_id);

CREATE INDEX idx_products_workspace_id_supplier
    ON products (workspace_id, supplier_id);


CREATE TABLE IF NOT EXISTS stocks (
    id bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    uuid uuid DEFAULT gen_random_uuid() NOT NULL UNIQUE,
    product_id bigint UNIQUE NOT NULL,
    workspace_id bigint REFERENCES workspaces(id) NOT NULL,
    quantity bigint DEFAULT 0 check ( quantity >= 0 ) NOT NULL,
    created_at timestamptz DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at timestamptz DEFAULT CURRENT_TIMESTAMP NOT NULL,
    version INT NOT NULL DEFAULT 0,

    CONSTRAINT fk_stocks_product FOREIGN KEY (product_id)
        REFERENCES products(id) ON DELETE CASCADE
);

CREATE INDEX idx_stocks_workspace_id_id
    ON stocks (workspace_id, id);