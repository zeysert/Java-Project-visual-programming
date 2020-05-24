package data;

public enum IndividualStat {
    CONFIRMED("C") {
        public double getValue(CountryCovidStats country) {
            return country.getConfirmed();
        }
    },
    DEATHS("D") {
        @Override
        public double getValue(CountryCovidStats country) {
            return country.getDeaths();
        }
    },
    RECOVERED("R") {
        @Override
        public double getValue(CountryCovidStats country) {
            return country.getRecovered();
        }
    };

    private final String symbol;

    IndividualStat(String symbol) {
        this.symbol = symbol;
    }

    public abstract double getValue(CountryCovidStats country);

}
