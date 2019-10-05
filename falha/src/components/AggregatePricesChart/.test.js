import React from 'react';

import { shallow } from 'enzyme';

import AggregatePricesChart from '.';

describe('AggregatePricesChart component', () => {
  it('is expected render without crashing', () => {
    shallow(<AggregatePricesChart />);
  });
});
