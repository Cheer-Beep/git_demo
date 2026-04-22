import java.util.*;

/**
 * 学生成绩管理系统
 * 用于测试Java基础语法、集合框架、面向对象编程等
 */
public class StudentGradeSystem {
    private List<Student> students;
    private Scanner scanner;

    public StudentGradeSystem() {
        students = new ArrayList<>();
        scanner = new Scanner(System.in);
    }

    // 主菜单
    public void start() {
        while (true) {
            System.out.println("\n========== 学生成绩管理系统 ==========");
            System.out.println("1. 添加学生");
            System.out.println("2. 为学生添加课程成绩");
            System.out.println("3. 显示所有学生信息及平均分");
            System.out.println("4. 按平均分排名（降序）");
            System.out.println("5. 查找学生");
            System.out.println("6. 删除学生");
            System.out.println("7. 统计信息（最高/最低平均分）");
            System.out.println("8. 退出");
            System.out.print("请选择操作：");
            
            int choice = getIntInput();
            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    addGradeForStudent();
                    break;
                case 3:
                    displayAllStudents();
                    break;
                case 4:
                    rankByAverage();
                    break;
                case 5:
                    searchStudent();
                    break;
                case 6:
                    deleteStudent();
                    break;
                case 7:
                    showStatistics();
                    break;
                case 8:
                    System.out.println("感谢使用，再见！");
                    return;
                default:
                    System.out.println("无效输入，请重新选择！");
            }
        }
    }

    // 添加学生
    private void addStudent() {
        System.out.print("请输入学生ID：");
        String id = scanner.nextLine().trim();
        if (findStudentById(id) != null) {
            System.out.println("学生ID已存在！");
            return;
        }
        System.out.print("请输入学生姓名：");
        String name = scanner.nextLine().trim();
        Student student = new Student(id, name);
        students.add(student);
        System.out.println("学生添加成功！");
    }

    // 为学生添加课程成绩
    private void addGradeForStudent() {
        System.out.print("请输入学生ID：");
        String id = scanner.nextLine().trim();
        Student student = findStudentById(id);
        if (student == null) {
            System.out.println("未找到该学生！");
            return;
        }
        System.out.print("请输入课程名称：");
        String course = scanner.nextLine().trim();
        System.out.print("请输入成绩（0-100）：");
        double score = getDoubleInput();
        if (score < 0 || score > 100) {
            System.out.println("成绩范围应为0-100！");
            return;
        }
        student.addGrade(course, score);
        System.out.println("成绩添加成功！");
    }

    // 显示所有学生信息
    private void displayAllStudents() {
        if (students.isEmpty()) {
            System.out.println("暂无学生数据！");
            return;
        }
        System.out.println("\n所有学生信息：");
        System.out.println("--------------------------------------------------");
        for (Student s : students) {
            System.out.println(s);
        }
        System.out.println("--------------------------------------------------");
    }

    // 按平均分排名
    private void rankByAverage() {
        if (students.isEmpty()) {
            System.out.println("暂无学生数据！");
            return;
        }
        List<Student> sorted = new ArrayList<>(students);
        sorted.sort((s1, s2) -> Double.compare(s2.getAverage(), s1.getAverage()));
        System.out.println("\n===== 学生平均分排名（降序）=====");
        int rank = 1;
        for (Student s : sorted) {
            System.out.printf("%d. %s (%s) - 平均分: %.2f%n", rank++, s.getName(), s.getId(), s.getAverage());
        }
    }

    // 查找学生
    private void searchStudent() {
        System.out.print("请输入学生ID或姓名：");
        String keyword = scanner.nextLine().trim();
        List<Student> results = new ArrayList<>();
        for (Student s : students) {
            if (s.getId().equalsIgnoreCase(keyword) || s.getName().equalsIgnoreCase(keyword)) {
                results.add(s);
            }
        }
        if (results.isEmpty()) {
            System.out.println("未找到匹配的学生！");
        } else {
            System.out.println("找到 " + results.size() + " 名学生：");
            for (Student s : results) {
                System.out.println(s);
            }
        }
    }

    // 删除学生
    private void deleteStudent() {
        System.out.print("请输入要删除的学生ID：");
        String id = scanner.nextLine().trim();
        Student student = findStudentById(id);
        if (student == null) {
            System.out.println("学生不存在！");
            return;
        }
        students.remove(student);
        System.out.println("学生删除成功！");
    }

    // 统计信息
    private void showStatistics() {
        if (students.isEmpty()) {
            System.out.println("暂无学生数据！");
            return;
        }
        double maxAvg = -1, minAvg = 101;
        Student best = null, worst = null;
        for (Student s : students) {
            double avg = s.getAverage();
            if (avg > maxAvg) {
                maxAvg = avg;
                best = s;
            }
            if (avg < minAvg) {
                minAvg = avg;
                worst = s;
            }
        }
        System.out.println("\n===== 统计信息 =====");
        if (best != null) {
            System.out.printf("最高平均分: %.2f (学生: %s %s)%n", maxAvg, best.getName(), best.getId());
        }
        if (worst != null) {
            System.out.printf("最低平均分: %.2f (学生: %s %s)%n", minAvg, worst.getName(), worst.getId());
        }
        System.out.printf("全体学生平均分: %.2f%n", getOverallAverage());
    }

    // 辅助方法：通过ID查找学生
    private Student findStudentById(String id) {
        for (Student s : students) {
            if (s.getId().equals(id)) {
                return s;
            }
        }
        return null;
    }

    // 辅助方法：获取整数输入
    private int getIntInput() {
        while (true) {
            try {
                int value = Integer.parseInt(scanner.nextLine().trim());
                return value;
            } catch (NumberFormatException e) {
                System.out.print("请输入有效的整数：");
            }
        }
    }

    // 辅助方法：获取浮点数输入
    private double getDoubleInput() {
        while (true) {
            try {
                double value = Double.parseDouble(scanner.nextLine().trim());
                return value;
            } catch (NumberFormatException e) {
                System.out.print("请输入有效的数字：");
            }
        }
    }

    // 计算所有学生的平均分
    private double getOverallAverage() {
        double sum = 0;
        for (Student s : students) {
            sum += s.getAverage();
        }
        return sum / students.size();
    }

    // 主方法
    public static void main(String[] args) {
        StudentGradeSystem system = new StudentGradeSystem();
        system.start();
    }
}

/**
 * 学生类
 */
class Student {
    private String id;
    private String name;
    private Map<String, Double> grades;  // 课程 -> 成绩

    public Student(String id, String name) {
        this.id = id;
        this.name = name;
        this.grades = new HashMap<>();
    }

    public String getId() { return id; }
    public String getName() { return name; }

    // 添加或更新成绩
    public void addGrade(String course, double score) {
        grades.put(course, score);
    }

    // 计算平均分（如果没有成绩则返回0）
    public double getAverage() {
        if (grades.isEmpty()) return 0;
        double sum = 0;
        for (double score : grades.values()) {
            sum += score;
        }
        return sum / grades.size();
    }

    // 获取所有课程及成绩
    public Map<String, Double> getGrades() {
        return new HashMap<>(grades);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("ID: %s | 姓名: %s | 平均分: %.2f\n", id, name, getAverage()));
        if (!grades.isEmpty()) {
            sb.append("   课程成绩：");
            for (Map.Entry<String, Double> entry : grades.entrySet()) {
                sb.append(String.format("%s=%.1f ", entry.getKey(), entry.getValue()));
            }
        } else {
            sb.append("   暂无成绩记录");
        }
        return sb.toString();
    }
}