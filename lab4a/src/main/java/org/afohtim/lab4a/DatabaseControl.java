package org.afohtim.lab4a;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class DatabaseControl {

    RWLock locker;
    private Database database;


    DatabaseControl(Database database) {
        locker = new RWLock();
        this.database = database;
    }

    ReadWriteLockInterface getReadWriteLock(){
        return locker;
    };

    String findPhoneByName(String name) {
        try {
            List<String> res = new ArrayList<>();
            BufferedReader fileReader = new BufferedReader(database.getReadHandler());
            String line = fileReader.readLine();
            while (line != null) {
                if (parseRow(line, 0).equals(name))
                    res.add(parseRow(line, 1));
                line = fileReader.readLine();
            }
            if(res.size() > 0) {
                return res.get(0);
            }
            else {
                return null;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    String findNameByPhone(String phone) {
        try {
            BufferedReader fileReader = new BufferedReader(database.getReadHandler());
            String line = fileReader.readLine();
            while (line != null) {
                if (parseRow(line, 1).equals(phone)) {
                    String user = parseRow(line, 0);
                    return user;
                }
                line = fileReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    String addRecord(String name, String phone){
        PrintWriter pw = null;
        try{
            pw = new PrintWriter(new BufferedWriter(database.getWriteHandler()));
            pw.println(name + " " + phone);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            pw.close();
        }
        return new String();
    }

    String deleteRecord(String name, String phone) {
        try {
            BufferedReader reader = new BufferedReader(database.getReadHandler());
            String curr;
            String remove = name + " " + phone;
            int cnt = 0;
            while ((curr = reader.readLine()) != null) {
                String trimmedLine = curr.trim();
                if (trimmedLine.equals(remove)) break;
                cnt++;
            }
            reader.close();
            if (curr != null) {
                database.removeLines(cnt, 1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String();
    }

    private static String parseRow(String line, int columnNumber) {
        return line.split("\\s+")[columnNumber];
    }
}
