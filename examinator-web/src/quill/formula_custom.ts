import Embed from "quill/blots/embed";

class FormulaCustom extends Embed {

  static blotName = 'formula';
  static className = 'ql-formula';
  static tagName = 'SPAN';


  static create(value: string) {
    // @ts-expect-error - either this or expanding interface Window with katex property
    if (window.katex == null) {
      throw new Error('Formula module requires KaTeX.');
    }
    const node = super.create(value) as Element;
    if (typeof value === 'string') {
      // @ts-expect-error - either this or expanding interface Window with katex property
      window.katex.render(value, node, {
        throwOnError: false,
        errorColor: '#f00',
        output: 'html'
      });
      node.setAttribute('data-value', value);
    }
    return node;
  }

  static value(domNode: HTMLElement) {
    return domNode.getAttribute('data-value');
  }

  html() {
    return (this.domNode as HTMLElement).outerHTML;
  }
}

export default FormulaCustom;
