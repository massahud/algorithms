package com.massahud.algorithms.sequencediscoverer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

public class SequenceDiscovererTest {

  @Test
  public void isSequenceDiscoverable_shouldBePossibleToOrderEmpty() {
    SequenceDiscoverer sequenceDiscoverer = new SequenceDiscoverer();
    assertThat(sequenceDiscoverer.isSequenceDiscoverable()).isTrue();
  }

  @Test
  public void isSequenceDiscoverable_shouldBePossibleToOrderSingleSequence() {
    SequenceDiscoverer sequenceDiscoverer = new SequenceDiscoverer();
    sequenceDiscoverer.addSubsequence(1, 2, 3, 4, 5);
    assertThat(sequenceDiscoverer.isSequenceDiscoverable()).isTrue();
  }

  @Test
  public void isSequenceDiscoverable_shouldBePossibleToOrderAMoreComplexExample() {
    SequenceDiscoverer sequenceDiscoverer = new SequenceDiscoverer();
    // 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13
    sequenceDiscoverer.addSubsequence(1, 2, 3, 4, 5);
    sequenceDiscoverer.addSubsequence(2, 6, 7, 8);
    sequenceDiscoverer.addSubsequence(8, 10);
    sequenceDiscoverer.addSubsequence(4, 8, 9);
    sequenceDiscoverer.addSubsequence(11, 12, 13);
    sequenceDiscoverer.addSubsequence(9, 10, 11);
    sequenceDiscoverer.addSubsequence(3, 5, 6);

    assertThat(sequenceDiscoverer.isSequenceDiscoverable()).isTrue();
  }

  @Test
  public void isSequenceDiscoverable_shouldNotBePossibleToOrderMultipleInitialValues() {
    SequenceDiscoverer sequenceDiscoverer = new SequenceDiscoverer();
    sequenceDiscoverer.addSubsequence(1, 3);
    sequenceDiscoverer.addSubsequence(2, 3);
    assertThat(sequenceDiscoverer.isSequenceDiscoverable()).isFalse();
  }

  @Test
  public void isSequenceDiscoverable_shouldNotBePossibleToOrderInternalMultipleOptions() {
    SequenceDiscoverer sequenceDiscoverer = new SequenceDiscoverer();
    sequenceDiscoverer.addSubsequence(1, 3, 10, 20);
    sequenceDiscoverer.addSubsequence(3, 10, 12);
    assertThat(sequenceDiscoverer.isSequenceDiscoverable()).isFalse();
  }

  @Test
  public void isSequenceDiscoverable_shouldNotBePossibleToOrderCycles() {
    SequenceDiscoverer sequenceDiscoverer = new SequenceDiscoverer();
    sequenceDiscoverer.addSubsequence(1, 3, 10, 20);
    sequenceDiscoverer.addSubsequence(10, 12, 20);
    sequenceDiscoverer.addSubsequence(12, 3);
    assertThat(sequenceDiscoverer.isSequenceDiscoverable()).isFalse();
  }

  @Test
  public void getSequence_shouldThrowExceptionWhenNotDiscoverable() {
    SequenceDiscoverer sequenceDiscoverer = new SequenceDiscoverer();
    sequenceDiscoverer.addSubsequence(1);
    sequenceDiscoverer.addSubsequence(2);
    assertThatThrownBy(() -> sequenceDiscoverer.getSequence()).
        isInstanceOf(SequenceNotDiscoverableException.class);
  }

  @Test
  public void getSequence_shouldReturnEmptySequence() {
    SequenceDiscoverer sequenceDiscoverer = new SequenceDiscoverer();
    assertThat(sequenceDiscoverer.getSequence()).isEmpty();
  }

  @Test
  public void getSequence_shouldReturnComplexSequence() {
    SequenceDiscoverer sequenceDiscoverer = new SequenceDiscoverer();
    // 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13
    sequenceDiscoverer.addSubsequence(1, 2, 3, 4, 5);
    sequenceDiscoverer.addSubsequence(2, 6, 7, 8);
    sequenceDiscoverer.addSubsequence(8, 10);
    sequenceDiscoverer.addSubsequence(4, 8, 9);
    sequenceDiscoverer.addSubsequence(11, 12, 13);
    sequenceDiscoverer.addSubsequence(9, 10, 11);
    sequenceDiscoverer.addSubsequence(3, 5, 6);

    assertThat(sequenceDiscoverer.getSequence()).
        containsExactly(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13);
  }
}


