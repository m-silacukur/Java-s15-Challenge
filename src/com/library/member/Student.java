package com.library.member;

public class Student extends MemberRecord {

    private final String studentId;
    private final String department;

    public Student(String memberId, String name, String address,
                   String phoneNo, String dateOfMembership,
                   String studentId, String department) {
        super(memberId, "Student", name, address, phoneNo, dateOfMembership);
        this.studentId  = studentId;
        this.department = department;
    }

    public String getStudentId()  { return studentId; }
    public String getDepartment() { return department; }

    @Override
    public String getMemberType() { return "Öğrenci"; }

    @Override
    public void display() {
        super.display();
        System.out.printf("  - Öğr. No  : %-26s│%n", studentId);
        System.out.printf("  - Bölüm    : %-26s│%n", department);

    }
}