package optional4j.test.autogen;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class CollaboratorClassTest {

    @Test
    public void getNullOrder() {

        // given
        CollaboratorClass collaboratorClass = new CollaboratorClass();

        // when
        AbstractOrder order = collaboratorClass.getOrder();

        // then
        assertThat(AbstractOrder.class.isAssignableFrom(order.getClass())).isTrue();
        assertThat(order).isInstanceOf(NullOrder.class);
        assertThat(order.getId()).isEqualTo(0);
        assertThat(order.getIdText()).isEqualTo("");
        assertThat(order.getByte()).isEqualTo((byte) 0);
        assertThat(order.getCustomer().isNull()).isTrue();
    }

    @Test
    public void getNonNullOrderWithNullCustomer() {

        // given
        CollaboratorClass collaboratorClass = new CollaboratorClass();
        collaboratorClass.order = new CollaboratorClass.Order();

        // when
        AbstractOrder order = collaboratorClass.getOrder();

        // then
        assertThat(AbstractOrder.class.isAssignableFrom(order.getClass())).isTrue();
        assertThat(order).isInstanceOf(CollaboratorClass.Order.class);
        assertThat(order.getId()).isEqualTo(123);
        assertThat(order.getIdText()).isEqualTo("123");
        assertThat(order.getByte()).isEqualTo((byte) 11);
        assertThat(order.getCustomer().isNull()).isTrue();
    }

    @Test
    public void getNonNullOrderWithNonNullCustomer() {

        // given
        CollaboratorClass collaboratorClass = new CollaboratorClass();
        collaboratorClass.order = new CollaboratorClass.Order();
        collaboratorClass.order.customer = new CollaboratorClass.Customer();

        // when
        AbstractOrder order = collaboratorClass.getOrder();

        // then
        assertThat(AbstractOrder.class.isAssignableFrom(order.getClass())).isTrue();
        assertThat(order).isInstanceOf(CollaboratorClass.Order.class);
        assertThat(order.getId()).isEqualTo(123);
        assertThat(order.getIdText()).isEqualTo("123");
        assertThat(order.getByte()).isEqualTo((byte) 11);
        assertThat(order.getCustomer().isNull()).isFalse();
    }
}
