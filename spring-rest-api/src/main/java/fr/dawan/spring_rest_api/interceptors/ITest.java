package fr.dawan.spring_rest_api.interceptors;

public interface ITest {
    void m1();
    default void m2(){};
    static void m3(){};
}

class MyClas{
    public static void main(String[] args) {
        ITest i = new Calcul();
        ITest i2 = new ITest() {
            @Override
            public void m1() {

            }
        };
    }
}

class Calcul implements ITest{

    @Override
    public void m1() {

    }
}