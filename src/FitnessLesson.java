import java.util.Date;
import java.util.List;

public class FitnessLesson {
    enum FitnessType {
        Spin,Yoga,Bodysculpt,Zumba,Aquacise,BoxFit
    };

    private FitnessType fitnessType;
    private Date datetime;

    private List<Customer> students;

    private int price;

}
