import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.cw_2.exception.NotEnoughQuestionsException;
import pro.sky.cw_2.model.Question;
import pro.sky.cw_2.service.impl.ExaminerServiceImpl;
import pro.sky.cw_2.service.impl.JavaQoestionService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ExaminerServiceImplTest {

    @Mock
    private JavaQoestionService javaQoestionService;

    @InjectMocks
    private ExaminerServiceImpl examinerService;

    private final List<Question> javaQuestions = new ArrayList<>();

    @BeforeEach
    public void beforeEach() {
        javaQuestions.clear();

        javaQuestions.addAll(
                Stream.of(
                        new Question("Вопрос по Java - 1", "Ответ по Java - 1"),
                        new Question("Вопрос по Java - 2", "Ответ по Java - 2"),
                        new Question("Вопрос по Java - 3", "Ответ по Java - 3")
                ).collect(Collectors.toSet())
        );
        when(javaQoestionService.getAll()).thenReturn(javaQuestions);
    }

    @Test
    public void getQuestionsNegativeTest() {
        assertThatExceptionOfType(NotEnoughQuestionsException.class)
                .isThrownBy(() -> examinerService.getQuestions(-1));
    }

    @Test
    public void getQuestionsPositiveTest() {
        when(javaQoestionService.getRandomQuestion()).thenReturn(
                new Question("Вопрос по Java - 1", "Ответ по Java - 1"),
                new Question("Вопрос по Java - 1", "Ответ по Java - 1"),
                new Question("Вопрос по Java - 1", "Ответ по Java - 1"),
                new Question("Вопрос по Java - 3", "Ответ по Java - 3")
                );

        assertThat(examinerService.getQuestions(2))
                .hasSize(2)
                .containsExactlyInAnyOrder(
                        new Question("Вопрос по Java - 1", "Ответ по Java - 1"),
                        new Question("Вопрос по Java - 3", "Ответ по Java - 3")
                );
    }
}
