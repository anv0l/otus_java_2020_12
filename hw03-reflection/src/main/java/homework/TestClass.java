package homework;

import java.nio.BufferOverflowException;

public class TestClass {
    private final Long id;
    private String name;
    private double x;
    private double y;
    private double rotation; // в радианах
    private double velocity;

    public TestClass(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Before
    public void init(double x, double y, double rotation, double velocity) {
        this.x = x;
        this.y = y;
        this.rotation = rotation;
        this.velocity = Math.abs(velocity); // скорость всегда >=0, направление меняется с помщью rotation
    }

    public void setRotationRad(double rotation) {
        this.rotation = rotation;
    }

    public void setRotationDeg(double degree) {
        this.rotation = Math.PI * degree / 180;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    public void move() {
        double dx = Math.cos(rotation) * velocity;
        double dy = Math.sin(rotation) * velocity;
        this.x += dx;
        this.y += dy;
        //System.out.println("Moved to " + this.x + ", " + this.y);
    }

    @Test
    void checkVelocity() {
        double x = this.x;
        double y = this.y;
        move();
        double calcVelocity = Math.sqrt(Math.pow(this.x - x, 2) + Math.pow(this.y - y , 2));
        if ( calcVelocity != this.velocity ) {
            System.out.println( calcVelocity + " vs. " + this.velocity);
            throw new AssertionError();
        }
    }

    @Test
    void willAlwaysThrowException() {
        throw new BufferOverflowException();
    }

    @Test
    void willAlwaysSucceed() {
    }

    @Override
    @After
    public String toString() {
        return "TestClass: " +
                name + "(" + id + ")"+ '\n' +
                "position: " + x + ", " + y + '\n' +
                "moving at " + rotation + " rad angle" +
                " at " + velocity + " speed.";
    }
}
