import React from 'react';
import PropTypes from 'prop-types';

import './style.scss';
import { Layout } from 'antd';

import FruitHeader from '../FruitHeader';
import FruitContent from '../FruitContent';

const { Header, Content } = Layout;

const FruitContainer = (props) => {
  const { data } = props;
  return (
    <>
      <Header>
        <FruitHeader />
      </Header>
      {/* <div style={{ margin: '40px' }}> */}
      <Layout className='fruit-container'>
        {/* <Layout className='fruit-content'> */}
        <Content className='fruit-wrapper'>
          <FruitContent data={data} />
        </Content>
        {/* </Layout> */}
      </Layout>
      {/* </div> */}
    </>
  );
};

FruitContainer.propTypes = {
  data: PropTypes.array
};

export default FruitContainer;
