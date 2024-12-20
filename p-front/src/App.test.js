import { render, screen } from '@testing-library/react';
import App from './App';
import Main from './Main';

test('renders learn react link', () => {
  render(<Main />);
  const linkElement = screen.getByText(/learn react/i);
  expect(linkElement).toBeInTheDocument();
});
