import React from 'react';

import { shallow } from 'enzyme';

import LatestPricesChart from '.';

describe('LatestPricesChart component', () => {
  it('is expected render without crashing', () => {
    shallow(<LatestPricesChart />);
  });
});
