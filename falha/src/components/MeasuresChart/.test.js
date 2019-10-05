import React from 'react';

import { shallow } from 'enzyme';

import MeasuresChart from '.';

describe('MeasuresChart component', () => {
  it('is expected render without crashing', () => {
    shallow(<MeasuresChart />);
  });
});
