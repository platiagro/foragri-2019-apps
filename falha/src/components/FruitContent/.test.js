import React from 'react';

import { shallow } from 'enzyme';

import FruitContent from '.';

describe('FruitContent component', () => {
  it('is expected render without crashing', () => {
    shallow(<FruitContent />);
  });
});
