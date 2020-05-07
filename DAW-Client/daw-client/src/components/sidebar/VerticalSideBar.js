import React from 'react';
import PropTypes from 'prop-types'
import {
  Icon,
  Menu,
  Sidebar,
} from 'semantic-ui-react'

const VerticalSidebar = () => (
    <Sidebar
      as={Menu}
      animation="overlay"
      direction="left"
      icon='labeled'
      inverted
      vertical
      visible="false"
      width='thin'
    >
      <Menu.Item as='a'>
        <Icon name='home' />
        Home
      </Menu.Item>
      <Menu.Item as='a'>
        <Icon name='gamepad' />
        Games
      </Menu.Item>
      <Menu.Item as='a'>
        <Icon name='camera' />
        Channels
      </Menu.Item>
    </Sidebar>
  )

  VerticalSidebar.propTypes = {
    animation: PropTypes.string,
    direction: PropTypes.string,
    visible: PropTypes.bool
  }

class VerticalSideBar extends React.PureComponent {
    constructor(props) {
        super(props)
        this.state = {
            animation: 'overlay',
            direction: 'left',
            dimmed: true,
            visible: false,
          }
    }
    
    handleAnimationChange = (animation) => () =>
        this.setState((prevState) => ({ animation, visible: !prevState.visible }))

        

         
    render() {
        return VerticalSideBar
    }
  }
  
  export default VerticalSideBar