package com.example.nursevendor.pojo;

import java.io.Serializable;

public class NurseAddtoCart implements Serializable {

    private String nurseTakenId;
    private String nurseTakenDate;
    private String nurseTakenPrectionImage;
    private String nurseTakenDoctorRef;
    private String vendorID;
    private String vendorName;
    private String vendorArea;
    private String vendorPhone;
    String packageId;
    String packageName;
    String packagePrice;
    private String patient_id;
    private String PatientName;
    private String phone;
    private String email;
    private String status;

    public NurseAddtoCart() {

    }

    public NurseAddtoCart(String nurseTakenId, String nurseTakenDate, String nurseTakenPrectionImage, String nurseTakenDoctorRef, String vendorID, String vendorName, String vendorArea, String vendorPhone, String packageId, String packageName, String packagePrice, String patient_id, String patientName, String phone, String email, String status) {
        this.nurseTakenId = nurseTakenId;
        this.nurseTakenDate = nurseTakenDate;
        this.nurseTakenPrectionImage = nurseTakenPrectionImage;
        this.nurseTakenDoctorRef = nurseTakenDoctorRef;
        this.vendorID = vendorID;
        this.vendorName = vendorName;
        this.vendorArea = vendorArea;
        this.vendorPhone = vendorPhone;
        this.packageId = packageId;
        this.packageName = packageName;
        this.packagePrice = packagePrice;
        this.patient_id = patient_id;
        PatientName = patientName;
        this.phone = phone;
        this.email = email;
        this.status = status;
    }

    public String getNurseTakenId() {
        return nurseTakenId;
    }

    public void setNurseTakenId(String nurseTakenId) {
        this.nurseTakenId = nurseTakenId;
    }

    public String getNurseTakenDate() {
        return nurseTakenDate;
    }

    public void setNurseTakenDate(String nurseTakenDate) {
        this.nurseTakenDate = nurseTakenDate;
    }

    public String getNurseTakenPrectionImage() {
        return nurseTakenPrectionImage;
    }

    public void setNurseTakenPrectionImage(String nurseTakenPrectionImage) {
        this.nurseTakenPrectionImage = nurseTakenPrectionImage;
    }

    public String getNurseTakenDoctorRef() {
        return nurseTakenDoctorRef;
    }

    public void setNurseTakenDoctorRef(String nurseTakenDoctorRef) {
        this.nurseTakenDoctorRef = nurseTakenDoctorRef;
    }

    public String getVendorID() {
        return vendorID;
    }

    public void setVendorID(String vendorID) {
        this.vendorID = vendorID;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getVendorArea() {
        return vendorArea;
    }

    public void setVendorArea(String vendorArea) {
        this.vendorArea = vendorArea;
    }

    public String getVendorPhone() {
        return vendorPhone;
    }

    public void setVendorPhone(String vendorPhone) {
        this.vendorPhone = vendorPhone;
    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getPackagePrice() {
        return packagePrice;
    }

    public void setPackagePrice(String packagePrice) {
        this.packagePrice = packagePrice;
    }

    public String getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(String patient_id) {
        this.patient_id = patient_id;
    }

    public String getPatientName() {
        return PatientName;
    }

    public void setPatientName(String patientName) {
        PatientName = patientName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
