package fr.axa.formation.domain.comptes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class Operation {

	private final String id;
	private final String compteId;
	private final String label;
	private final BigDecimal montant;
	private final LocalDateTime date;

	protected Operation(final String id,
	                    final String compteId,
	                    final String label,
	                    final BigDecimal montant,
	                    final LocalDateTime date) {
		this.id = id;
		this.compteId = compteId;
		this.label = label;
		this.montant = montant;
		this.date = date;
	}

	public static Operation debit(final String compteId,
	                              final String label,
	                              final BigDecimal montant) {
		return Operation.newOperation(
				compteId,
				label,
				montant.multiply(BigDecimal.valueOf(-1))
		);
	}

	public static Operation credit(final String compteId,
	                               final String label,
	                               final BigDecimal montant) {
		return Operation.newOperation(
				compteId,
				label,
				montant
		);
	}

	private static Operation newOperation(final String compteId,
	                                      final String label,
	                                      final BigDecimal montant) {
		return new Builder()
				.withCompteId(compteId)
				.withLabel(label)
				.withMontant(montant)
				.withDate(LocalDateTime.now())
				.build();
	}

	public String getId() {
		return this.id;
	}

	public String getCompteId() {
		return this.compteId;
	}

	public String getLabel() {
		return this.label;
	}

	public BigDecimal getMontant() {
		return this.montant;
	}

	public LocalDateTime getDate() {
		return this.date;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		final Operation operation = (Operation) o;
		return Objects.equals(id, operation.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	public Builder toBuilder() {
		return new Builder(this);
	}

	public static class Builder {

		private String id;
		private String compteId;
		private String label;
		private BigDecimal montant;
		private LocalDateTime date;

		public Builder() {
		}

		public Builder(final Operation operation) {
			this.id = operation.id;
			this.compteId = operation.compteId;
			this.label = operation.label;
			this.montant = operation.montant;
			this.date = operation.date;
		}

		public Builder withId(final String id) {
			this.id = id;
			return this;
		}

		public Builder withCompteId(final String compteId) {
			this.compteId = compteId;
			return this;
		}

		public Builder withLabel(final String label) {
			this.label = label;
			return this;
		}

		public Builder withMontant(final BigDecimal montant) {
			this.montant = montant;
			return this;
		}

		public Builder withDate(final LocalDateTime date) {
			this.date = date;
			return this;
		}

		public Operation build() {
			return new Operation(
					this.id,
					this.compteId,
					this.label,
					this.montant,
					this.date
			);
		}
	}
}
