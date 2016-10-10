import Component from 'metal-component/src/Component';
import Soy from 'metal-soy/src/Soy';
import templates from './Contributor.soy';

/**
 * Contributor Component
 */
class Contributor extends Component {}

// Register component
Soy.register(Contributor, templates);

export default Contributor;