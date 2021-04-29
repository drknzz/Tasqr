package com.example.tasqr.classes;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.tasqr.Utilities;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Company implements Parcelable {

    private String id;
    private String name;
    private String description;
    private String owner;
    private ArrayList<String> workers;
    private ArrayList<String> managers;
    private ArrayList<String> projectsId;

    /* Constructors */
    public Company(String id, String name, String description, String owner, ArrayList<String> workers) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.owner = owner;
        this.workers = workers;
        managers = new ArrayList<String>();
        projectsId = new ArrayList<String>();
        projectsId.add("root");
    }

    public Company() {}

    protected Company(Parcel in) {
        id = in.readString();
        name = in.readString();
        description = in.readString();
        owner = in.readString();
        workers = in.createStringArrayList();
        managers = in.createStringArrayList();
        projectsId = in.createStringArrayList();
    }

    public static final Creator<Company> CREATOR = new Creator<Company>() {
        @Override
        public Company createFromParcel(Parcel in) {
            return new Company(in);
        }

        @Override
        public Company[] newArray(int size) {
            return new Company[size];
        }
    };

    /* Getters */
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getOwner() {
        return owner;
    }

    public ArrayList<String> getWorkers() {
        return workers;
    }

    public ArrayList<String> getManagers() {
        return managers;
    }

    public ArrayList<String> getProjectsId() {
        return projectsId;
    }

    /* Setters */
    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setWorkers(ArrayList<String> workers) {
        this.workers = workers;
    }

    public void setManagers(ArrayList<String> managers) {
        this.managers = managers;
    }

    public void setProjectsId(ArrayList<String> projectsId) {
        this.projectsId = projectsId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(owner);
        dest.writeStringList(workers);
        dest.writeStringList(managers);
        dest.writeStringList(projectsId);
    }

    public void leaveCompany(String logged_mail, String position) {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://tasqr-android-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference rootRef = database.getReference();
        DatabaseReference usersRef = rootRef.child("Users");
        DatabaseReference companiesRef = rootRef.child("Companies");

        Query q = usersRef.orderByChild("mail").equalTo(logged_mail);
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user;
                ArrayList<String> tmp;
                for (DataSnapshot ds : snapshot.getChildren()) {
                    user = ds.getValue(User.class);
                    tmp = user.getCompanies();
                    tmp.remove(id);
                    user.setCompanies(tmp);
                    usersRef.child(user.getId()).child("companies").setValue(tmp);
                    workers.remove(user.getMail());
                    if (position.equals("manager")) {
                        tmp = user.getManagedCompanies();
                        tmp.remove(name);
                        usersRef.child(user.getId()).child("managedCompanies").setValue(tmp);
                        managers.remove(user.getMail());
                    }

                    companiesRef.child(id).setValue(Company.this);
                }

                for (String project : projectsId) {
                    //TODO usuwanie z projektow
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
