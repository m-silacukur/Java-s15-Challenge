package com.library.member;

public class Faculty extends MemberRecord {

    private final String facultyId;
    private final String department;

    public Faculty(String memberId, String name, String address,
                   String phoneNo, String dateOfMembership,
                   String facultyId, String department) {
        super(memberId, "Faculty", name, address, phoneNo, dateOfMembership);
        this.facultyId  = facultyId;
        this.department = department;
    }

    public String getFacultyId()  { return facultyId; }
    public String getDepartment() { return department; }

    @Override
    public String getMemberType() { return "Öğretim Üyesi"; }

    @Override
    public void display() {
        super.display();
        System.out.printf("  - Personel : %-26s│%n", facultyId);
        System.out.printf("  - Bölüm    : %-26s│%n", department);

    }
}

