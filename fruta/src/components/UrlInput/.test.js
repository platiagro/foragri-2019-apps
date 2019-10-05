import React from 'react';

import { shallow } from 'enzyme';

import SetUrlContent from '.';

describe('SetUrlContent component', () => {
  it('is expected render without crashing', () => {
    shallow(<SetUrlContent />);
  });
});
