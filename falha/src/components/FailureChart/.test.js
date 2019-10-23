import React from 'react';

import { shallow } from 'enzyme';

import FailureChart from '.';

describe('FailureChart component', () => {
  it('is expected render without crashing', () => {
    shallow(<FailureChart />);
  });
});
