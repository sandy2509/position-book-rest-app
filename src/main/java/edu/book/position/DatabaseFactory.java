package edu.book.position;


import edu.book.position.vendor.H2Database;

public interface DatabaseFactory {
    static void buildDbWithSampleData(DatabaseVendorType dbType) {

        switch (dbType) {
            //intentional fallthrough done here
            case H2:
            default:
                H2Database.instantiateAndInitializeDB();
        }
    }
}
