import java.util.Scanner;

class Student
{
    int rollno,sem;
    String name,address;
    void getdata(int r,int s,String n,String a)
    {
        rollno=r;
        sem=s;
        name=n;
        address=a;
    }
    void printdata()
    {
        System.out.println(rollno+"\t"+sem+"\t"+name+"\t"+address);
    }
}
public class StudentDemo
{
    public static void main(String[] args)
    {
        Student s1=new Student();
        Scanner s=new Scanner(System.in);
        System.out.println("Enter the number of students:");
        int n=s.nextInt();
        Student arr[];
        for(int i=0;i<n;i++)
        {
            System.out.println("Enter the Rollno:");
            int r=s.nextInt();
            System.out.println("Enter the Semester:");
            int sem=s.nextInt();
            System.out.println("Enter the Name:");
            String name=s.nextLine();
            System.out.println("Enter the Address:");
            String address=s.nextLine();
            arr[i]=new Student();
            arr[i].getdata(r,sem,name,address);
        }
        System.out.println("RollNo\tSemester\tName\tAddress");
        for(int i=0;i<n;i++)
        {
            arr[i].printdata();
        }
    }
}

