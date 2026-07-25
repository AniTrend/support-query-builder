# sample/src/main/kotlin/co/anitrend/support/query/builder/sample/data/database/

## Responsibility

Room database definition for the sample data layer. `Store` declares the person and pet tables, exposes DAOs, and provides a test-only in-memory creation helper.

## Design Patterns

Uses an abstract `RoomDatabase` with a companion factory and `RoomDatabase.Callback` seeding hook. The database is intentionally in-memory and permits main-thread queries for sample and test convenience.

## Data & Control Flow

`create(context)` builds `Store`, attaches the creation callback, and returns the Room instance. On first creation, the callback starts a SQLite transaction, inserts 20 people and up to 7 pets, marks success, and ends the transaction.

## Integration Points

Integrates with `PersonEntity`, `PetEntity`, `PersonDao`, `PetDao`, Android `ContentValues`, `SQLiteDatabase.CONFLICT_IGNORE`, and `SupportSQLiteDatabase` transaction APIs.
