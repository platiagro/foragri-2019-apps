import React from 'react';

import { shallow } from 'enzyme';

import FruitContainer from '.';

describe('FruitContainer component', () => {
  it('is expected render without crashing', () => {
    shallow(<FruitContainer />);
  });
});
