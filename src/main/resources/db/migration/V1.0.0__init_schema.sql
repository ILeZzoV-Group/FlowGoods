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

CREATE TABLE IF NOT EXISTS suppliers (
    id bigint GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    uuid uuid DEFAULT gen_random_uuid() NOT NULL UNIQUE,
    name varchar(63) NOT NULL,
    workspace_id bigint REFERENCES workspaces(id) NOT NULL,
    created_at timestamptz DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at timestamptz DEFAULT CURRENT_TIMESTAMP NOT NULL,
    version INT NOT NULL DEFAULT 0
);

CREATE INDEX workspace_index_on_suppliers ON suppliers(workspace_id);

CREATE TABLE IF NOT EXISTS contacts (
    supplier_id BIGINT PRIMARY KEY,
    phone varchar(15),
    email varchar(255),
    link text,
    version INT NOT NULL DEFAULT 0,
    CONSTRAINT fk_contact_supplier FOREIGN KEY (supplier_id) REFERENCES suppliers (id) ON DELETE CASCADE
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
